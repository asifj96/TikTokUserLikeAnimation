package com.demo.tittokanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: List<Int>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profile_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.profileImage.setImageResource(userList[position])

//        val currentImageRes = userList[position]

        // Perform cross-fade animation on the single ImageView
//        crossFade(holder.profileImage, currentImageRes)

//        // Load image into ImageView (assuming you have an ImageView in your item layout)
//        holder.profileImage.setImageResource(userList[position])
//
//        // Fade-in
//        holder.profileImage.startAnimation(
//            AnimationUtils.loadAnimation(
//                holder.itemView.context,
//                R.anim.fade_in
//            )
//        )
//
//        // Fade-out
//        holder.profileImage.startAnimation(
//            AnimationUtils.loadAnimation(
//                holder.itemView.context,
//                R.anim.fade_out
//            )
//        )

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


    override fun getItemCount(): Int {
        return userList.size // Create an infinite loop
    }
}
