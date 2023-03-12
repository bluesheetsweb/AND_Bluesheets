package com.bluesheets.ui.switch_org_n_workspace.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluesheets.databinding.ActivitySwitchOrgAndWorkBinding
import src.wrapperutil.uicomponent.ViewPager

class SwitchOrgAndWorkActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwitchOrgAndWorkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchOrgAndWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager(binding.viewpager)
        binding.tabs.setupWithViewPager(binding.viewpager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOrganiszationsList(), "Organizations")
        adapter.addFragment(FragmentWorkSpacesList(), "Workspaces")
        viewPager.adapter = adapter
    }
}