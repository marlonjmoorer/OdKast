package com.marlonjmoorer.odkast

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.*
import com.marlonjmoorer.odkast.Adapters.HomeAdapter
import com.marlonjmoorer.odkast.Helpers.*
import com.marlonjmoorer.odkast.Models.PodcastFeed
import com.marlonjmoorer.odkast.Services.MediaService
import com.marlonjmoorer.odkast.Services.MediaService.MusicBinder
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class MainActivity : AppCompatActivity(), Observer {


    private var slidePanel: SlidingUpPanelLayout? = null
    private var mini_player: LinearLayout? = null
    private var header: LinearLayout? = null
    private var mediaService: MediaService? = null
    private var mediaOverservable: MediaService.MediaObservable? = null
    private var seekBar: SeekBar? = null
    private var mediaPlayer: MediaPlayer? = null
    private var elapsedText: TextView? = null
    private var durationText: TextView? = null
    private var actionSearch: Button? = null
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var toolbar = find<Toolbar>(R.id.toolbar)
        var viewPager = find<ViewPager>(R.id.viewpager)
        var tabLayout = find<TabLayout>(R.id.tabs)
        mini_player = find<LinearLayout>(R.id.mini_player)
        header = find<LinearLayout>(R.id.header)
        seekBar = find<SeekBar>(R.id.seekBar);
        elapsedText = find<TextView>(R.id.elapsed)
        durationText = find<TextView>(R.id.duration)


        setSupportActionBar(toolbar)
        slidePanel = find<SlidingUpPanelLayout>(R.id.sliding_layout)

        viewPager.adapter = HomeAdapter(supportFragmentManager)
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager)

        slidePanel?.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {


            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                mini_player?.fade(1 - (slideOffset))
                header!!.fade(slideOffset)

            }

            override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {

            }
        })

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                if (fromUser) {
                    mediaPlayer?.let { it.seekTo(progress) }
                    elapsedText?.text = progress.toTime()
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data?.hasExtra("ep") == true) {
            var ep = data.getStringExtra("ep").parseTo<PodcastFeed.EpisodeItem>()
            this.setUpPlayback(ep)
            slidePanel?.expand()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView?.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        val textChangeListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                // this is your adapter that will be filtadapter.getFilter().filter(newText);
                println("on text chnge text: " + newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView?.onActionViewCollapsed()
                println("on query submit: " + query)
                return false
            }
        }
        searchView?.setOnQueryTextListener(textChangeListener)
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent?.hasExtra("ep") == true) {
            var ep = intent.getStringExtra("ep").parseTo<PodcastFeed.EpisodeItem>()
            this.setUpPlayback(ep)
            slidePanel?.expand()
        }
    }

    override fun onStart() {
        super.onStart()

        var i = intentFor<MediaService>()
        bindService(i, musicConnection, Context.BIND_AUTO_CREATE)
        startService(i)

    }

    override fun onBackPressed() {
        when (slidePanel?.panelState) {
            PanelState.EXPANDED -> slidePanel?.collapse()
            else -> super.onBackPressed()
        }
    }

    private fun setUpPlayback(episode: PodcastFeed.EpisodeItem) {
        with(episode) {
            find<ImageView>(R.id.mp_poster)?.loadUrl(thumbnail)
            find<TextView>(R.id.mp_title)?.text = title
           // find<TextView>(R.id.mp_show_title)?.text = "SHOW!!"
            find<TextView>(R.id.ep_title)?.text = title
            doAsync {
                val bitmap = Picasso.with(this@MainActivity).load(thumbnail).get();


                uiThread {
                    var media = MediaService.MediaObject(enclosure.link, bitmap, title, "")
                    mediaService?.setMedia(media)
                    seekBar!!.max = mediaPlayer?.duration!!
                    elapsedText?.text = 0.toTime()
                    durationText?.text = mediaPlayer?.duration!!.toTime()
                    find<LinearLayout>(R.id.background).backgroundDrawable = BitmapDrawable(bitmap)
                }
            }

            var playBtn = find<ImageButton>(R.id.ep_play)
            var play_button_mini = find<Button>(R.id.mp_play)


            play_button_mini.onClick {
                mediaService?.play_pause()
            }
            playBtn.onClick {
                mediaService?.play_pause()

            }

        }
    }



    fun startSeekBar() = doAsync(asycHandler()) {

        while (mediaPlayer?.isPlaying!!) {

            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {

                e.printStackTrace()
            }
            uiThread {

                seekBar?.setProgress(mediaPlayer?.getCurrentPosition() ?: 0)
                elapsedText?.text = "${mediaPlayer?.getCurrentPosition()?.toTime()}"
                //seekBar?.setProgress(mediaPlayer?.getCurrentPosition()?:0)
                //elapsedText?.text= mediaPlayer?.getCurrentPosition()?.toTime()
                //toast("Nope")
            }

        }
    }


    private val musicConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MusicBinder

            mediaService = binder.service
            mediaService!!.IsPlayingObservable?.addObserver(this@MainActivity)
            mediaPlayer = binder.mediaPlayer

        }

        override fun onServiceDisconnected(name: ComponentName) {
            //musicBound = false
        }
    }


    override fun update(o: Observable?, arg: Any?) {
        var playBtn = find<ImageButton>(R.id.ep_play)
        var mp_play = find<Button>(R.id.mp_play)

        if (o is MediaService.MediaObservable) {

            if (o.IsPlaying) {

                playBtn.imageResource = R.drawable.icons8_pause_filled
                mp_play.backgroundResource = R.drawable.icons8_pause_filled
                startSeekBar()
            } else {
                playBtn.imageResource = R.drawable.icons8_play_filled
                mp_play.backgroundResource = R.drawable.icons8_play_filled
            }


        }
    }


}



