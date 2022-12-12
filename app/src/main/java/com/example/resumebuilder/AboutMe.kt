package com.example.resumebuilder

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.resumebuilder.models.CVDataBase
import kotlinx.android.synthetic.main.fragment_about_me.*
import kotlinx.coroutines.launch

class AboutMe : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_me, container, false)

        launch {
            context?.let {
                var dao = CVDataBase(it).getDao()
                var userWithAllData = dao.getAllUsers()[0]
                txtName.text = "${userWithAllData.user.firstName} ${userWithAllData.user.lastName}"
                txtAddress.text = userWithAllData.user.address
                txtBio.text = userWithAllData.user.bio
                txtEmail.text = userWithAllData.user.emailAddress
                txtPhone.text = userWithAllData.user.phoneNumber
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.video_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.introduce-> {
                val intent = Intent(this.context, IntroduceActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
    //completed my part
}