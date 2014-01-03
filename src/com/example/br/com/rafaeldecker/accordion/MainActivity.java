package com.example.br.com.rafaeldecker.accordion;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {

	/**
	 * Views that represents the first block of content
	 */
	private LinearLayout mFirstContentLayout;
	private Button mFirstExpandButton;
	private ExpandCollapseAnimator mFirstAnimator;

	/**
	 * Views that represents the second block of content
	 */
	private LinearLayout mSecondContentLayout;
	private Button mSecondExpandButton;
	private ExpandCollapseAnimator mSecondAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting views reference
		mFirstContentLayout = (LinearLayout) findViewById(R.id.firstContentLayout);
		mFirstExpandButton = (Button) findViewById(R.id.firstExpandButton);
		mSecondContentLayout = (LinearLayout) findViewById(R.id.secondContentLayout);
		mSecondExpandButton = (Button) findViewById(R.id.secondExpandButton);

		// Set click listeners on buttons
		mFirstExpandButton.setOnClickListener(this);
		mSecondExpandButton.setOnClickListener(this);

		// Set the content visible to make sure we will get the right height on onWindowFocusChanged
		mFirstContentLayout.setVisibility(View.INVISIBLE);
		mSecondContentLayout.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		// Ready to get views size and now it's possible to create animators
		// objects
		mFirstAnimator = new ExpandCollapseAnimator(mFirstContentLayout, new Point(0, mFirstContentLayout.getHeight()));
		mSecondAnimator = new ExpandCollapseAnimator(mSecondContentLayout, new Point(0, mSecondContentLayout.getHeight()));

		// To ensure the content layout will start on hidden state
		mFirstContentLayout.setVisibility(View.GONE);
		mSecondContentLayout.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.firstExpandButton: {
			if (mFirstAnimator != null) {
				mFirstAnimator.animate(ExpandCollapseAnimator.DEFAULT_ANIMATION_DURATION);
			}
			break;
		}

		case R.id.secondExpandButton: {
			if (mSecondAnimator != null) {
				mSecondAnimator.animate(ExpandCollapseAnimator.DEFAULT_ANIMATION_DURATION);
			}
			break;
		}
		}

	}

}
