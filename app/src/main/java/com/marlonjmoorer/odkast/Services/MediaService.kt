package com.marlonjmoorer.odkast.Services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.marlonjmoorer.odkast.R
import java.util.*


/**
 * Created by marlonmoorer on 6/4/17.
 */
class MediaService : Service(),MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener{


     val IsPlayingObservable:MediaObservable= MediaObservable()


    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
      return true
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mediaPlayer: MediaPlayer? = null
    private var nowPlaying:MediaObject?=null



    override fun onBind(intent: Intent?): IBinder? {
       return MusicBinder()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onCreate() {
        instance= this
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
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, buildNotification())
    }

    fun showNotification(){
        startForeground(1, buildNotification())
    }
    fun hideNotification(){
        stopForeground(true)
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent?.action==null && mediaPlayer==null) {
            mediaPlayer = MediaPlayer().apply() {
                setOnBufferingUpdateListener(this@MediaService)
                setOnPreparedListener(this@MediaService)
                setOnErrorListener(this@MediaService)
                setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
        }else{
            handleIntent(intent!!)
        }
        return START_STICKY
    }



    private fun buildNotification():Notification{

        var remoteView = RemoteViews(packageName, R.layout.remote_view).apply{

             var playIntent=Intent(applicationContext,MediaService::class.java).apply {
                 action= PLAY_PAUSE
             }
             var ffIntent=Intent(applicationContext,MediaService::class.java).apply {
                action= FF
             }
             var rrIntent=Intent(applicationContext,MediaService::class.java).apply {
                action= RR
             }

            setOnClickPendingIntent(R.id.note_play, PendingIntent.getService(this@MediaService,1,playIntent,0))
            setOnClickPendingIntent(R.id.note_ff, PendingIntent.getService(this@MediaService,1,ffIntent,0))
            setOnClickPendingIntent(R.id.note_rr, PendingIntent.getService(this@MediaService,1,rrIntent,0))

            setImageViewBitmap(R.id.note_poster,nowPlaying?.image)
            setTextViewText(R.id.note_title,nowPlaying?.title)
            setTextViewText(R.id.note_show_title,nowPlaying?.subTitle)

            var buttonResource=if(mediaPlayer!!.isPlaying) R.drawable.icons8_circled_pause_filled else R.drawable.icons8_circled_play_filled
            setImageViewResource(R.id.note_play,buttonResource)

        }


        //val intent = Intent(applicationContext, MediaService::class.java)
        //val pendingI= PendingIntent.getService(this,1, intent,0)


        val notification = Notification.Builder(this)
                .setContentTitle("TITLE")
                .setContentText("Text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Tick")
                .setContent(remoteView)
                .build()

        return notification

    }
    private fun  handleIntent(intent: Intent) {

        with(mediaPlayer!!){

            when(intent.action){
                PLAY_PAUSE->{  play_pause()}
                BACK->{}
                NEXT->{}
                FF->{seekTo(currentPosition-30000)}
                RR->{seekTo(currentPosition+30000)}
                else->{}
            }
        }
    }

    companion object{
        private var instance: MediaService? = null
        private val PLAY_PAUSE="PLAY_PAUSE"
        private val BACK="BACK"
        private val NEXT="NEXT"
        private val FF="FF"
        private val RR="RR"
        private val STOP="STOP"
    }

    inner class MusicBinder : Binder() {
        internal val service: MediaService
            get() = instance!!
    }

    data class MediaObject(val url:String,val image:Bitmap,val title:String, val subTitle:String){}

    inner class MediaObservable : Observable() {

        internal val IsPlaying:Boolean
            get() = mediaPlayer?.isPlaying()?:false

        fun emitChange() {
            setChanged()
            notifyObservers()
        }
    }

}