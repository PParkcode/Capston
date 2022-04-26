package com.example.testing1.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.DetailCampaignBinding
import com.example.testing1.viewModel.DetailCampaignViewModel
import com.google.android.material.tabs.TabLayout
import com.iamport.sdk.domain.core.Iamport
import java.text.SimpleDateFormat
import java.util.*


private val TAG="tag1"
class DetailCampaign:AppCompatActivity() {
    private lateinit var binding: DetailCampaignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val campaignViewModel by viewModels<DetailCampaignViewModel>()
        super.onCreate(savedInstanceState)
        var intent=getIntent()
        val campaignId: Int? =intent.getIntExtra("id",0)
        Log.d(TAG,"intent get: ${campaignId.toString()}")

        binding= DataBindingUtil.setContentView<DetailCampaignBinding>(this, R.layout.detail_campaign)
        setContentView(binding.root)
        var actionBar: ActionBar?
        actionBar=supportActionBar
        actionBar?.hide()

        binding.viewModel=campaignViewModel
        binding.lifecycleOwner=this

        val currentDate = currentDate()
        currentDate.dateToString("yyyyMMdd")

        campaignViewModel._campaignData=instance.getCampaignDetail(campaignId.toString())
        Log.d("tag1","campaignViewModel: "+campaignViewModel.campaignData?.value)




        val fragmentIntroduce=FragIntroduce()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout,fragmentIntroduce).commit()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab!!.position){
                    0-> {
                        Log.d(TAG,"position 0 진입")

                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragmentIntroduce).commit()
                    }
                    1-> {
                        val fragmentCurrentData=FragCurrentData()
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragmentCurrentData).commit()
                    }
                    2-> {
                        val fragmentReview=FragReview()
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragmentReview).commit()
                    }

                }
            }

        })


        binding.donateBtn.setOnClickListener{
            Iamport.init(this)
        }




    }

    fun Date.dateToString(format: String, local : Locale = Locale.getDefault()): String{
        val formatter = SimpleDateFormat(format, local)
        return formatter.format(this)
    }

    fun currentDate(): Date{
        return Calendar.getInstance().time
    }
}