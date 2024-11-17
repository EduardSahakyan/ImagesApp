package com.example.imagesapp.presentation.auth.signin

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
import com.example.imagesapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding()
        setupListeners()
        collectEffects()
    }

    private fun setupListeners() = with(binding) {
        buttonSignIn.setOnClickListener {
            viewModel.signIn(editTextEmail.text.toString(), editTextPassword.text.toString())
        }
        buttonCreateAccount.setOnClickListener {
            navigateToSignUp()
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
                    SignInEffect.NavigateToHome -> navigateToHome()
                    is SignInEffect.ShowToast -> showToast(effect.message)
                    SignInEffect.ShowEmptyEmailMessage -> setEmailFieldErrorMessage(getString(R.string.error_empty_email))
                    SignInEffect.ShowEmptyPasswordMessage -> setPasswordFieldErrorMessage(getString(R.string.error_empty_password))
                    SignInEffect.ShowInvalidEmailFormatMessage -> setEmailFieldErrorMessage(getString(R.string.error_invalid_email_format))
                    SignInEffect.ShowInvalidPasswordLengthMessage -> setPasswordFieldErrorMessage(getString(R.string.error_password_length_invalid))
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

    private fun navigateToHome() {
        val navController = findNavController()
        navController.navigate(AppGraphDirections.actionGlobalMainFlow())
    }

    private fun navigateToSignUp() {
        val navController = findNavController()
        navController.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}