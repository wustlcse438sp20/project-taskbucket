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
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskbucket.R
//import com.example.taskbucket.activites.mBottomNav
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
        //viewpager_main.adapter = fragmentAdapter
        //viewpager_main.offscreenPageLimit = 3
        //tabs_main.setupWithViewPager(viewpager_main)

        val mViewPager: ViewPager2 = view.findViewById(R.id.viewpager_main)
        val mTabLayout: TabLayout = view.findViewById(R.id.tabs_main)

        var fragList = arrayListOf<Fragment>()
        fragList.add(MonthFragment())
        fragList.add(WeekFragment())
        fragList.add(DayFragment())
        var nameList = arrayListOf<String>()
        nameList.add("Month")
        nameList.add("Week")
        nameList.add("Day")

        val mViewPagerAdapter =
            CalendarViewPagerAdapter(
                this,
                fragList
            )

        mViewPager.adapter = mViewPagerAdapter

        TabLayoutMediator(mTabLayout, mViewPager,true){ tab, position ->
            tab.text = nameList[position]
        }.attach()





    }

    class CalendarViewPagerAdapter(fragment: Fragment, fragList: ArrayList<Fragment>) : FragmentStateAdapter(fragment){
        var fragList = fragList
        override fun getItemCount() : Int = 3
        override fun createFragment(position: Int): Fragment {
            return fragList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

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





