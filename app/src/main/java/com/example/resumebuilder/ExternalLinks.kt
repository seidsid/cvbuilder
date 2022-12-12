package com.example.resumebuilder

import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.resumebuilder.models.CVDataBase
import com.example.resumebuilder.models.ExternalLinksTypesEnum
import kotlinx.android.synthetic.main.fragment_external_links.*
import kotlinx.coroutines.launch

class ExternalLinks : BaseFragment() {

    lateinit var webViewItem : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        launch {
            context?.let {
                var dao = CVDataBase(it).getDao()
                var userWithAllData = dao.getAllUsers()[0]
                for(externalLink in userWithAllData.externalLinks)
                {
                    when(externalLink.externalLinkType)
                    {
                        ExternalLinksTypesEnum.LinkedIn ->
                            menu.add(0, externalLink.externalLinkType.ordinal, 0, "LinkedIn")
                                .setIcon(R.drawable.linkedin)
                                .setTooltipText(externalLink.URL)
                                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

                        ExternalLinksTypesEnum.Facebook ->
                            menu.add(0, externalLink.externalLinkType.ordinal, 0, "Facebook")
                                .setIcon(R.drawable.facebook)
                                .setTooltipText(externalLink.URL)
                                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

                        ExternalLinksTypesEnum.GitHub ->
                            menu.add(0, externalLink.externalLinkType.ordinal, 0, "GitHub")
                                .setIcon(R.drawable.github)
                                .setTooltipText(externalLink.URL)
                                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

                        ExternalLinksTypesEnum.WebSite ->
                            menu.add(0, externalLink.externalLinkType.ordinal, 0, "WebSite")
                                .setTooltipText(externalLink.URL)
                                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                    }
                }
            }
        }
        //inflater.inflate(R.menu.main_menu, menu)
        //return super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_external_links, container, false)
        webViewItem = view.findViewById<WebView>(R.id.webView)
        webViewItem.webViewClient = WebViewClient()
        webViewItem.settings.javaScriptEnabled = true
        webViewItem.settings.builtInZoomControls = true
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        webViewItem.loadUrl(item.tooltipText.toString())
        return super.onOptionsItemSelected(item)
    }
}