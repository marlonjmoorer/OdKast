package com.marlonjmoorer.odkast.Services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.marlonjmoorer.odkast.R
import com.squareup.picasso.Picasso
import java.util.*


/**
 * Created by marlonmoorer on 6/4/17.
 */
class MediaService : Service(),MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{


    val IsPlayingObservable:MediaObservable= MediaObservable()




    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
      return true
    }


    var mediaPlayer: MediaPlayer? = null
    private var nowPlaying:MediaObject?=null



    override fun onBind(intent: Intent?): IBinder? {
       return MediaBinder()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        play_pause()
    }

    var  rrIntent: Intent?=null
    var playIntent:Intent?=null
    var ffIntent: Intent? = null


    override fun onCreate() {
        instance= this
        playIntent=Intent(applicationContext,MediaService::class.java).apply {
            action= PLAY_PAUSE
        }
        ffIntent=Intent(applicationContext,MediaService::class.java).apply {
            action= FF
        }
        rrIntent=Intent(applicationContext,MediaService::class.java).apply {
            action= RR
        }
    }



    fun setMedia(media:MediaObject){
        nowPlaying=media
        try {
            with(instance?.mediaPlayer!!){
                stop()
                reset()
                setDataSource(nowPlaying?.url)
                prepare()
            }
        }catch (ex:Exception){
            Log.d("SERVICE",ex.stackTrace.contentDeepToString())
        }
        showNotification()
    }

    fun play_pause(){

        with(mediaPlayer!!){
            if(isPlaying){
                pause()
            }else{
                start()
            }
        }
        IsPlayingObservable.emitChange()
       updateNotification()
    }

    fun updateNotification(){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_ID, buildNotification())
    }

    fun showNotification(){
        startForeground(NOTIFICATION_ID, buildNotification())
    }
    fun hideNotification(){
        stopForeground(true)
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent?.action==null && mediaPlayer==null) {
            mediaPlayer = MediaPlayer().apply() {

                setOnPreparedListener(this@MediaService)
                setOnErrorListener(this@MediaService)
                setAudioStreamType(AudioManager.STREAM_MUSIC);
                setOnCompletionListener {
                    IsPlayingObservable.emitChange()
                    updateNotification()
                }
            }
        }else{
            handleIntent(intent!!)
        }
        return START_STICKY
    }



    private fun buildNotification():Notification{

        var remoteView = RemoteViews(packageName, R.layout.remote_view).apply{

            setOnClickPendingIntent(R.id.note_play, PendingIntent.getService(this@MediaService,1,playIntent,0))
            setOnClickPendingIntent(R.id.note_ff, PendingIntent.getService(this@MediaService,1,ffIntent,0))
            setOnClickPendingIntent(R.id.note_rr, PendingIntent.getService(this@MediaService,1,rrIntent,0))

           // setImageViewBitmap(R.id.note_poster,nowPlaying?.imageUrl)
            setTextViewText(R.id.note_title,nowPlaying?.title)
            setTextViewText(R.id.note_show_title,nowPlaying?.subTitle)

            var buttonResource=if(mediaPlayer!!.isPlaying) R.drawable.icons8_circled_pause_filled else R.drawable.icons8_circled_play_filled
            setImageViewResource(R.id.note_play,buttonResource)

        }






        val notification = Notification.Builder(this)
                .setContentTitle("TITLE")
                .setContentText("Text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Tick")
                .setContent(remoteView)
                .build()

        Picasso.with(this)
                .load(nowPlaying?.imageUrl)
                .into(remoteView, R.id.note_poster, NOTIFICATION_ID, notification)

        return notification

    }
    private fun  handleIntent(intent: Intent) {

        with(mediaPlayer!!){

            when(intent.action){
                PLAY_PAUSE->{  play_pause()}
                //STOP->{reset()}
                BACK->{}
                NEXT->{}
                RESET->{stop()
                        reset()
                }
                FF->{seekTo(currentPosition+30000)}
                RR->{seekTo(currentPosition-30000)}
                else->{}
            }
        }
    }

    companion object{
        private var instance: MediaService? = null
        val PLAY_PAUSE="PLAY_PAUSE"
        val BACK="BACK"
        val NEXT="NEXT"
        val FF="FF"
        val RR="RR"
        val STOP="STOP"
        val RESET="RESET"
        private val NOTIFICATION_ID=1337
    }

    inner class MediaBinder : Binder() {
        internal val service: MediaService
            get() = instance!!
        internal val mediaPlayer:MediaPlayer
            get()= instance?.mediaPlayer!!
    }

    data class MediaObject(val url:String, val imageUrl:String?, val title:String, val subTitle:String)

    inner class MediaObservable : Observable() {

        internal val IsPlaying:Boolean
            get() = mediaPlayer?.isPlaying()?:false

        fun emitChange() {
            setChanged()
            notifyObservers()
        }
    }

}