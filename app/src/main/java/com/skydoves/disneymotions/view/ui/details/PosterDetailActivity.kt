/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.disneymotions.view.ui.details

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import com.skydoves.disneymotions.R
import com.skydoves.disneymotions.base.DatabindingActivity
import com.skydoves.disneymotions.databinding.ActivityPosterDetailBinding
import com.skydoves.disneymotions.extensions.applyMaterialTransform
import com.skydoves.disneymotions.model.Poster
import com.skydoves.whatif.whatIfNotNullAs
import org.koin.android.viewmodel.ext.android.getViewModel

class PosterDetailActivity : DatabindingActivity() {

  private val binding: ActivityPosterDetailBinding by binding(R.layout.activity_poster_detail)
  private val posterId: Long by bundle(EXTRA_POSTER_ID, -1)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val poster = getViewModel<PosterDetailViewModel>().getPoster(posterId)
    applyMaterialTransform(poster.name)

    binding.apply {
      this.poster = poster
      lifecycleOwner = this@PosterDetailActivity
      activity = this@PosterDetailActivity
      container = detailContainer
      fab = fabMore
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    private const val EXTRA_POSTER_ID = "EXTRA_POSTER_ID"

    fun startActivity(context: Context?, startView: View, poster: Poster) {
      context.whatIfNotNullAs<Activity> {
        it.intentOf<PosterDetailActivity> {
          putExtra(EXTRA_POSTER_ID, poster.id)
          val options = ActivityOptions.makeSceneTransitionAnimation(
            it,
            startView,
            poster.name
          )
          startActivity(it, options.toBundle())
        }
      }
    }
  }
}
