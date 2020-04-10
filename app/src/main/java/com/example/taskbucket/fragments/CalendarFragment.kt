package com.example.taskbucket.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskbucket.R
import com.example.taskbucket.activites.mBottomNav
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = MyPagerAdapter(activity!!.supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        viewpager_main.offscreenPageLimit = 3
        tabs_main.setupWithViewPager(viewpager_main)
    }

    class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount() : Int {
            return 3
        }

        override fun getItem(position: Int) : Fragment {
            return when (position) {
                0 -> { MonthFragment() }
                1 -> { WeekFragment() }
                else -> {
                    DayFragment()
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0-> "Month"
                 1 -> "Week"
                else -> "Day"
            }
        }

    }


}
