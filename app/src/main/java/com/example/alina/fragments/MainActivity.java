package com.example.alina.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private Fragment frag_1 = new FragmentOne();
    ;
    private Fragment frag_2 = new FragmentTwo();
    ;

    final Fragment frag1 = new FragmentOne();
    final Fragment frag2 = new FragmentTwo();

    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction;

    Button add;
    Button delete;
    Button replace;
    Fragment active = frag1;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        add = (Button) findViewById( R.id.button_add );
        delete = (Button) findViewById( R.id.button_delete );
        replace = (Button) findViewById( R.id.button_replace );
        linear = (LinearLayout) findViewById( R.id.liner1 );


        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById( R.id.bottom_navigation );
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );

        navigation.enableItemShiftingMode( true );

        manager.beginTransaction()
                .add( R.id.frame_for_fragments, frag1, "2" )
                .hide( frag1 ) // hide - скріть фрагмент
                .commit();
        manager.beginTransaction()
                .add( R.id.frame_for_fragments, frag2, "3" )
                .hide( frag2 )
                .commit();

        add.setOnClickListener( add_word );
        delete.setOnClickListener( delete_word );
        replace.setOnClickListener( replace_word );

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_translator:
                    linear.setVisibility( View.VISIBLE );
                    manager.beginTransaction()
                            .hide( frag1 )
                            .commit();
                    manager.beginTransaction()
                            .hide( frag2 )
                            .commit();
                    return true;


                case R.id.action_vocabulary:
                    manager.beginTransaction()
                            .hide( active )
                            .show( frag1 )
                            .commit();
                    active = frag1;
                    return true;


                case R.id.action_train:
                    manager.beginTransaction()
                            .hide( active )
                            .show( frag2 )
                            .commit();
                    active = frag2;
                    return true;
            }
            return false;
        }
    };

    View.OnClickListener add_word = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transaction = manager.beginTransaction();
//            if (manager.findFragmentByTag( FragmentTwo.TAG  ) != null ){ // если уникальный элемент уже есть, то ..
//                transaction.replace(R.id.frame_for_fragments,  frag_1, FragmentOne.TAG  );
//            } if () {
//                transaction.add(R.id.frame_for_fragments,  frag_1, FragmentOne.TAG  );
//            }
//            transaction.addToBackStack(null);
//            transaction.commit();

            if (manager.findFragmentByTag( FragmentOne.TAG ) != null) {
                transaction.replace( R.id.frame_for_fragments, frag_1, FragmentOne.TAG );
            } else {
                transaction.add( R.id.frame_for_fragments, frag_1, FragmentOne.TAG );
            }
            transaction.addToBackStack( null );
            transaction.commit();
        }


    };

    View.OnClickListener delete_word = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transaction = manager.beginTransaction();
            transaction.remove( frag_1 );
            transaction.remove( frag_2 );
            transaction.commit();
        }

    };

    View.OnClickListener replace_word = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transaction = manager.beginTransaction();
            if (manager.findFragmentByTag( FragmentOne.TAG ) != null) {
                transaction.replace( R.id.frame_for_fragments, frag_2, FragmentTwo.TAG );
            }
            transaction.addToBackStack( null );
            transaction.commit();
        }

    };
}

