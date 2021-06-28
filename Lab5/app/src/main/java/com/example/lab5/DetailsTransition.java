package com.example.lab5;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.view.View;

import androidx.fragment.app.Fragment;

public class DetailsTransition extends TransitionSet {
    public DetailsTransition(){
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).addTransition(new
                ChangeTransform()).addTransition(new ChangeImageTransform());
    }
}
