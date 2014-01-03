package com.example.br.com.rafaeldecker.accordion;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

public class ExpandCollapseAnimator {

	/**
	 * Default value to animation duration
	 */
	public static final int DEFAULT_ANIMATION_DURATION = 300;

	/**
	 * Layout that will be animated
	 */
	private LinearLayout mLayout;

	/**
	 * Controller boolean to lock a new animation until the first one is not
	 * ended
	 */
	private boolean mIsAnimationBlocked;

	/**
	 * Animation objects
	 */
	private ValueAnimator mExpandAnimation;
	private ValueAnimator mCollapseAnimation;

	public ExpandCollapseAnimator(LinearLayout layoutToBeExpandedCollapsed, Point offsetLimit) {
		mLayout = layoutToBeExpandedCollapsed;
		mIsAnimationBlocked = false;

		if (mLayout == null || offsetLimit == null) {
			throw new IllegalArgumentException("The arguments \"layoutToBeExpandedCollapsed\" and \"offsetLimit\" must not be null");
		}

		mExpandAnimation = ValueAnimator.ofObject(new HeightEvaluator(mLayout), offsetLimit.x, offsetLimit.y);
		mExpandAnimation.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				mIsAnimationBlocked = true;
				mLayout.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mIsAnimationBlocked = false;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});

		mCollapseAnimation = ValueAnimator.ofObject(new HeightEvaluator(mLayout), offsetLimit.y, offsetLimit.x);
		mCollapseAnimation.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				mIsAnimationBlocked = true;
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mIsAnimationBlocked = false;
				mLayout.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}

	/**
	 * Start the layout animation
	 * 
	 * @param int duration (DEFAULT VALUE =
	 *        ContentAnimator.DEFAULT_ANIMATION_DURATION)
	 */
	public void animate(int duration) {
		if (!mIsAnimationBlocked) {
			int animDuration = (duration > 0) ? duration : DEFAULT_ANIMATION_DURATION;
			boolean isExpanded = (mLayout.getVisibility() == View.VISIBLE) ? true : false;
			if (isExpanded) {
				
				// Do collapse animation
				mCollapseAnimation.setDuration(animDuration);
				mCollapseAnimation.start();
			} else {
				// Do expand animation
				mExpandAnimation.setDuration(animDuration);
				mExpandAnimation.start();
			}
		}
	}

	/**
	 * Inner class used to do the animation on Height of the parent layout
	 * 
	 * @author rafaeldecker
	 * 
	 */
	private class HeightEvaluator extends IntEvaluator {

		private View v;

		public HeightEvaluator(View v) {
			this.v = v;
		}

		@Override
		public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
			Integer num = super.evaluate(fraction, startValue, endValue);
			ViewGroup.LayoutParams params = v.getLayoutParams();
			params.height = num;
			v.setLayoutParams(params);
			return num;
		}
	}

}
