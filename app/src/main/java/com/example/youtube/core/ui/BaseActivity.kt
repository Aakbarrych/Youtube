package com.example.youtube.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding, VM: BaseViewModel> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected abstract fun inflateViewBinding(): VB
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding()
        setContentView(binding.root)

        isConnection()
        initViews()
        initViewModel()
        initListener()
        observe()
        setPlaylist()
    }

    open fun setPlaylist(){}

    open fun initViews() {} // Инициализация вью
    open fun isConnection() {} // Проверка на интернет
    open fun initViewModel() {} // Инициализация моделек
    open fun initListener() {} // Слушатель
    open fun observe(){} // Функция для изменяемых данных Live Mutable

}