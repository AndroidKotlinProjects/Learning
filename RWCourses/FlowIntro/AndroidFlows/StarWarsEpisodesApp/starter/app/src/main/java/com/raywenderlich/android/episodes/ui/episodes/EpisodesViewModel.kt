/* 
 * Copyright (c) 2020 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.episodes.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.episodes.model.Episode
import com.raywenderlich.android.episodes.model.EpisodeRepository
import com.raywenderlich.android.episodes.model.NoTrilogy
import com.raywenderlich.android.episodes.model.Trilogy
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class EpisodesViewModel @Inject constructor(
  private val episodeRepository: EpisodeRepository
) : ViewModel() {

  val snackbar: LiveData<String?>
    get() = _snackbar

  private val _snackbar = MutableLiveData<String?>()

  private val _spinner = MutableLiveData<Boolean>(false)

  val spinner: LiveData<Boolean>
    get() = _spinner

  private val trilogyChannel = ConflatedBroadcastChannel<Trilogy>()
  val episodesUsingFlow: Flow<List<Episode>> = trilogyChannel.asFlow()
    .flatMapLatest { trilogy ->
      _spinner.value = true
      if (trilogy == NoTrilogy) {
        episodeRepository.episodesFlow
      } else {
        episodeRepository.getEpisodesForTrilogyFlow(trilogy)
      }
    }.onEach {
      _spinner.value = false
    }.catch {
        throwable -> _snackbar.value = throwable.message
    }

  init {
    clearTrilogyNumber()

    loadData { episodeRepository.tryUpdateRecentEpisodesCache() }
  }

  fun setTrilogyNumber(num: Int) {
    trilogyChannel.offer(Trilogy(num))
    loadData { episodeRepository.tryUpdateRecentEpisodesForTrilogyCache(Trilogy(num)) }
  }

  private fun clearTrilogyNumber() {
    trilogyChannel.offer(NoTrilogy)
    loadData { episodeRepository.tryUpdateRecentEpisodesCache() }
  }

  fun onSnackbarShown() {
    _snackbar.value = null
  }

  private fun loadData(block: suspend () -> Unit): Job {
    return viewModelScope.launch {
      try {
        _spinner.value = true
        block()
      } catch (error: Throwable) {
        _snackbar.value = error.message
      } finally {
        _spinner.value = false
      }
    }
  }
}
