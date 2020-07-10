/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.devbyteviewer.R
import com.example.android.devbyteviewer.adapter.DevByteAdapter
import com.example.android.devbyteviewer.adapter.VideoClick
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.util.goneIfNotNull
import com.example.android.devbyteviewer.viewmodel.DevByteViewModel
import com.example.android.devbyteviewer.viewmodel.Factory
import kotlinx.android.synthetic.main.fragment_dev_byte.*

class DevByteFragment : Fragment(R.layout.fragment_dev_byte) {

    private val viewModel: DevByteViewModel by lazy {
        val application = requireNotNull(this.activity).application
        ViewModelProvider(this, Factory(application))
                .get(DevByteViewModel::class.java)
    }

    private val viewModelAdapter by lazy {
        DevByteAdapter(VideoClick {
            val packageManager = requireContext().packageManager ?: return@VideoClick
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if (intent.resolveActivity(packageManager) == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            }
            startActivity(intent)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclervDevByte.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        setObserver()
    }

    private fun setObserver() {
        viewModel.playlist.observe(viewLifecycleOwner, Observer {
            goneIfNotNull(progbarSpinner, it)
            it.apply {
                viewModelAdapter.videos = it
            }
        })
    }

    private val Video.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }
}