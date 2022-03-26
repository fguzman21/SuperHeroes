package fernando.com.superheroes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import fernando.com.superheroes.Views.SuperHeroActivity

class Splash : AppCompatActivity() {
    private var imglogo: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imglogo = findViewById(R.id.imglogo)
        startAnimations()
    }

    private fun startAnimations() {
        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_in_top)
        anim.reset()
        imglogo!!.clearAnimation()
        imglogo!!.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            intent = Intent(applicationContext, SuperHeroActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}