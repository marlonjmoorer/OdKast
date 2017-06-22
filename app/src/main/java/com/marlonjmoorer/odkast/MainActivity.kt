package com.marlonjmoorer.odkast

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.widget.*
import com.marlonjmoorer.odkast.Adapters.HomeAdapter
import com.marlonjmoorer.odkast.Fragments.SearchFragment
import com.marlonjmoorer.odkast.Helpers.*
import com.marlonjmoorer.odkast.Models.PodcastFeed
import com.marlonjmoorer.odkast.Services.MediaService
import com.marlonjmoorer.odkast.Services.MediaService.MediaBinder
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import org.jetbrains.anko.design.longSnackbar
import android.view.animation.ScaleAnimation




class MainActivity : AppCompatActivity(), Observer, OnPodcastSelectedLister {

    private var slidePanel: SlidingUpPanelLayout? = null
    private var mini_player: View? = null
    private var headerView: View? = null
    private var mediaService: MediaService? = null
    private var mediaOverservable: MediaService.MediaObservable? = null
    private var seekBar: SeekBar? = null
    private var mediaPlayer: MediaPlayer? = null
    private var elapsedText: TextView? = null
    private var durationText: TextView? = null
    private var actionSearch: Button? = null
    private var searchView: SearchView? = null
    private var ep_view: View? = null


    override fun onShowSelected(id: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main, ShowDetailFragment.newInstace(id))
                .addToBackStack(null)
                .commit()

    }

    override fun onEpisodeSelected(episode: PodcastFeed.EpisodeItem) {
        this.setUpPlayback(episode)
        //slidePanel?.expand()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database.pultus?.let {
          //  it.delete(Subscription())
        }


        var toolbar = find<Toolbar>(R.id.toolbar)
        var viewPager = find<ViewPager>(R.id.viewpager)
        var tabLayout = find<TabLayout>(R.id.tabs)


        mini_player = find(R.id.mini_player)
        headerView = find(R.id.header_view)
        seekBar = find<SeekBar>(R.id.seekBar)
        elapsedText = find<TextView>(R.id.elapsed)
        durationText = find<TextView>(R.id.duration)
        ep_view = findViewById(R.id.ep_view)
       // var mini_poster=find<ImageView>(R.id.mp_poster)



        setSupportActionBar(toolbar)
        slidePanel = find<SlidingUpPanelLayout>(R.id.sliding_layout)

        viewPager.adapter = HomeAdapter(supportFragmentManager)
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager)



        slidePanel?.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {


            override fun onPanelSlide(panel: View?, slideOffset: Float) {

                headerView?.fade(1-(slideOffset*2))
               // mini_player?.fade(1 - (slideOffset))
               //header!!.fade(slideOffset)
                var frame= findViewById(R.id.frame)
                //with(mini_poster){

                   /*val anim = ScaleAnimation(
                            mini_poster.scaleX*(1+slideOffset)
                            , mini_poster.scaleX
                            ,mini_poster.scaleY*(1+slideOffset)
                            ,mini_poster.scaleY
                            , Animation.ABSOLUTE, 0f
                            , Animation.ABSOLUTE, 0f) // Pivot point of Y scaling
                    anim.fillAfter = true // Needed to keep the result of the animation
                    mini_poster.startAnimation(anim)
                    */
                    frame.pivotX=0f
                    frame.pivotY=0f
                    frame?.scaleX=(slideOffset*5)+(1f)
                    frame?.scaleY=(slideOffset*5)+(1f)


                  //  frame.animate().setDuration(1).scaleX(slideOffset+(1f))
                   // frame.animate().setDuration(1).scaleY(slideOffset+(1f))
                 //  var params= FrameLayout.LayoutParams()
                   // params.width=(width*slideOffset).toInt()
                   // params.height=(height*slideOffset).toInt()
                   // mini_poster.layoutParams=params
               //}

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
        bindService(i, mediaConnection, Context.BIND_AUTO_CREATE)
        startService(i)
    }

    override fun onBackPressed() {
        // contentView?.loadingScreen(false)
        when (slidePanel?.panelState) {
            PanelState.EXPANDED -> slidePanel?.collapse()
            else -> super.onBackPressed()
        }
    }

    private fun setUpPlayback(episode: PodcastFeed.EpisodeItem) {
        with(episode) {
            if (mediaPlayer?.isPlaying!!) {
                mediaService?.play_pause()
                var i = intentFor<MediaService>().apply {
                    action = MediaService.STOP
                }
                startService(i)
            }

            find<ImageView>(R.id.mp_poster)?.loadUrl(thumbnail)
            find<TextView>(R.id.mp_title)?.text = title
            find<TextView>(R.id.ep_title)?.text = title
            find<TextView>(R.id.ep_show_title)?.text=""

            //contentView?.loadingScreen(true)
            doAsync(asycHandler()) {


                uiThread {

                    var media = MediaService.MediaObject(enclosure.link, thumbnail, title, "")
                    mediaService?.setMedia(media)
                    seekBar!!.max = mediaPlayer?.duration!!
                    elapsedText?.text = 0.toTime()
                    durationText?.text = mediaPlayer?.duration!!.toTime()

                     var mini_poster=find<ImageView>(R.id.mp_poster)
                    mini_poster.loadUrl(thumbnail)

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
            ep_view?.visibility = View.VISIBLE

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


    private val mediaConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MediaBinder

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_main, menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.action_open_search -> {
                var fragment = SearchFragment()
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main, fragment)
                        .addToBackStack("search")
                        .commit()
                invalidateOptionsMenu()

            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onSubscribe(id: String) {

        database.subscribe(id)

        longSnackbar(contentView!!, "Subcribed", "Undo", { v ->
            database.unSubscribe(id)
            longSnackbar(contentView!!, "Unsubscribed")
        })
    }

    override fun onUnSubscribe(id: String) {
        database.unSubscribe(id)
        longSnackbar(contentView!!, "UnSubcribed", "Undo", { v ->
            database.subscribe(id)
            longSnackbar(contentView!!, "Subscribed")
        })
    }
}



