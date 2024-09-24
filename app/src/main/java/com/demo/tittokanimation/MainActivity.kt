package com.demo.tittokanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var handler: Handler
    private var currentItemIndex = 0 // Track the current item index

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List of drawable resources for user images
        val userImages = listOf(
            R.drawable.user1,
            R.drawable.user2,
            R.drawable.user3,
            R.drawable.user4,
        )


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create and set the adapter
        adapter = UserAdapter(userImages)
        recyclerView.adapter = adapter

        handler = Handler(Looper.getMainLooper())

        // Add smooth scroll
        autoScroll(recyclerView, userImages)

    }

    private fun crossFade(imageView: ImageView, newImageRes: Int) {
        // Create fade-out animator
        val fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)
        fadeOut.duration = 500 // Duration for fade out

        // Create a listener to set the new image and fade-in animator
        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Set the new image resource after fade out
                imageView.setImageResource(newImageRes)

                // Create fade-in animator
                val fadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)
                fadeIn.duration = 500 // Duration for fade in
                fadeIn.start() // Start fade-in animation
            }
        })

        // Start the fade-out animation
        fadeOut.start()
    }

    private fun autoScroll(recyclerView: RecyclerView, userImages: List<Int>) {
        val scrollSpeed: Long = 60 // Time between each scroll (lower value = faster scrolling)
        val smoothScrollDistance = 10 // Number of pixels to scroll each iteration

        val runnable = object : Runnable {
            override fun run() {
                val itemCount = recyclerView.adapter?.itemCount ?: 0

                if (itemCount == 0) return

                recyclerView.smoothScrollBy(0, smoothScrollDistance)
                handler.postDelayed(this, scrollSpeed)
            }
        }

        handler.post(runnable)
    }


    /*
        private fun autoScroll(recyclerView: RecyclerView, userImages: List<Int>) {
            val scrollSpeed: Long = 2000 // Time before switching to the next image

            val runnable = object : Runnable {
                override fun run() {
                    val itemCount = recyclerView.adapter?.itemCount ?: 0

                    // Check if we have items to scroll through
                    if (itemCount == 0) return

                    // Get the current view holder
    //                val viewHolder =
    //                    recyclerView.findViewHolderForAdapterPosition(currentItemIndex) as? UserAdapter.UserViewHolder
    //                if (viewHolder != null) {
    //                    // Perform cross-fade animation
    //                    crossFade(viewHolder.profileImage, userImages[currentItemIndex])
    //                }

                    // Update the current item index for the next scroll
                    currentItemIndex = (currentItemIndex + 1) % itemCount // Loop back to the start

                    // Post the next scroll after the animation completes
    //                handler.postDelayed({
                    // Smooth scroll to the next item
                    recyclerView.smoothScrollToPosition(currentItemIndex)

    //                    // Check for the last item and perform cross-fade
    //                    val nextViewHolder =
    //                        recyclerView.findViewHolderForAdapterPosition(currentItemIndex) as? UserAdapter.UserViewHolder
    //                    if (nextViewHolder != null) {
    //                        crossFade(nextViewHolder.profileImage, userImages[currentItemIndex])
    //                    }

                    // Repost the runnable to continue the cycle
                    handler.postDelayed(this, 100)
    //                }, 50) // Adjust the delay to match the duration of your cross-fade animation
                }
            }

            handler.post(runnable)
        }
    */

}