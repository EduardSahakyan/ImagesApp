package com.example.imagesapp.presentation.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.imagesapp.AppGraphDirections
import com.example.imagesapp.R
import com.example.imagesapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding()
        setupListeners()
        collectEffects()
    }

    private fun setupListeners() = with(binding) {
        buttonSignUp.setOnClickListener {
            viewModel.singUp(
                editTextEmail.text.toString(),
                editTextPassword.text.toString(),
                editTextAge.text.toString()
            )
        }
    }

    private fun setupDataBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun collectEffects() {
        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    SignUpEffect.NavigateToHome -> navigateToHome()
                    is SignUpEffect.ShowToast -> showToast(effect.message)
                    SignUpEffect.ShowEmptyEmailMessage -> setEmailFieldErrorMessage(getString(R.string.error_empty_email))
                    SignUpEffect.ShowEmptyPasswordMessage -> setPasswordFieldErrorMessage(getString(R.string.error_empty_password))
                    SignUpEffect.ShowEmptyAgeMessage -> setAgeFieldErrorMessage(getString(R.string.error_empty_age))
                    SignUpEffect.ShowInvalidEmailFormatMessage -> setEmailFieldErrorMessage(getString(R.string.error_invalid_email_format))
                    SignUpEffect.ShowInvalidPasswordLengthMessage -> setPasswordFieldErrorMessage(getString(R.string.error_password_length_invalid))
                    SignUpEffect.ShowInvalidAgeRangeMessage -> setAgeFieldErrorMessage(getString(R.string.error_invalid_age_range))
                }
            }
        }
    }

    private fun setEmailFieldErrorMessage(message: String) {
        viewModel.setEmailErrorMessage(message)
    }

    private fun setPasswordFieldErrorMessage(message: String) {
        viewModel.setPasswordErrorMessage(message)
    }

    private fun setAgeFieldErrorMessage(message: String) {
        viewModel.setAgeErrorMessage(message)
    }

    private fun navigateToHome() {
        val navController = findNavController()
        navController.navigate(AppGraphDirections.actionGlobalMainFlow())
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}