package com.marlonjmoorer.odkast

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
import android.view.View
import android.widget.*
import com.marlonjmoorer.odkast.Adapters.HomeAdapter
import com.marlonjmoorer.odkast.Helpers.*
import com.marlonjmoorer.odkast.Services.MediaService
import com.marlonjmoorer.odkast.Services.MediaService.MusicBinder
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class MainActivity : AppCompatActivity(), Observer {


    override fun update(o: Observable?, arg: Any?) {
        var playBtn= find<Button>(R.id.ep_play)
        var mp_play= find<Button>(R.id.mp_play)

        if(o is MediaService.MediaObservable){

            if(o.IsPlaying) {

                playBtn.backgroundResource = R.drawable.icons8_pause_filled
                mp_play.backgroundResource = R.drawable.icons8_pause_filled
                startSeekBar()
            }
            else {
                playBtn.backgroundResource=R.drawable.icons8_play_filled
                mp_play.backgroundResource=R.drawable.icons8_play_filled
            }


        }
    }

    private var slidePanel: SlidingUpPanelLayout? = null
    private var  mini_player: LinearLayout?=null
    private var header:LinearLayout?=null
    private  var mediaService:MediaService?=null
    private  var mediaOverservable:MediaService.MediaObservable?=null
    private  var seekBar:SeekBar?=null
    private  var mediaPlayer:MediaPlayer?=null
    private  var elapsedText:TextView?=null
    private  var durationText:TextView?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (intent?.hasExtra("ep_id") == true) {
            this.setUpPlayback(intent?.getIntExtra("ep_id", 0)!!)
            slidePanel?.expand()
        }else {


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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data?.hasExtra("ep_id") == true) {
            this.setUpPlayback(data.getIntExtra("ep_id", 0))
            slidePanel?.expand()
        }
    }


    override fun onStart() {
        super.onStart()
       // if (playIntent == null) {
           // playIntent = Intent(this, MusicService::class.java)
        var i= intentFor<MediaService>()
        bindService(i, musicConnection, Context.BIND_AUTO_CREATE)
        startService(i)
      //  }
    }

    override fun onBackPressed() {
        when (slidePanel?.panelState) {
            PanelState.EXPANDED -> slidePanel?.collapse()
            else -> super.onBackPressed()
        }
    }


    private fun setUpPlayback(id: Int) {

        //var poster = find<ImageView>(R.id.poster)
        with(AudioSearch.getInstance(this)) {
            doAsync(asycHandler()) {

                var poster_url: String
                var ep = GetEpisodeById(id)

                if (ep.image_files.isEmpty()) {
                    var show = GetShowById(ep.show_id)
                    poster_url = show.image_files.first().url.full

                } else {
                    poster_url = ep.image_files.first().url.full
                }
                val bitmap = Picasso.with(this@MainActivity).load(poster_url).get();
                uiThread {
                   // poster.loadUrl(poster_url)
                    find<ImageView>(R.id.mp_poster)?.loadUrl(poster_url)
                    find<TextView>(R.id.mp_title)?.text=ep.title
                    find<TextView>(R.id.mp_show_title)?.text=ep.show_title
                    find<TextView>(R.id.ep_title)?.text= ep.title
                    find<LinearLayout>(R.id.background).backgroundDrawable= BitmapDrawable(bitmap)


                    var media = MediaService.MediaObject(ep.digital_location,bitmap,ep.title,ep.show_title)
                    mediaService?.setMedia(media)
                    seekBar!!.max=mediaPlayer?.duration!!
                    elapsedText?.text=(0 as Int).toTime()
                    durationText?.text=mediaPlayer?.duration!!.toTime()

                    var playBtn= find<Button>(R.id.ep_play)
                    var play_button_mini=find<Button>(R.id.mp_play)


                    play_button_mini.onClick {
                        mediaService?.play_pause()
                    }
                    playBtn.onClick {
                       mediaService?.play_pause()
                    }
                }
            }
        }

    }

    fun startSeekBar()= doAsync(asycHandler()) {

            while (mediaPlayer?.isPlaying!!){

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                    e.printStackTrace()
                }
                uiThread {

                    seekBar?.setProgress(mediaPlayer?.getCurrentPosition()?:0)
                    elapsedText?.text= "${mediaPlayer?.getCurrentPosition()?.toTime()}"
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
             mediaPlayer= binder.mediaPlayer

        }

        override fun onServiceDisconnected(name: ComponentName) {
            //musicBound = false
        }
    }




}



