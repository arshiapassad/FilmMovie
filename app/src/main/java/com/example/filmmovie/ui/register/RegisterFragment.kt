package com.example.filmmovie.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.filmmovie.R
import com.example.filmmovie.databinding.FragmentRegisterBinding
import com.example.filmmovie.models.register.BodyRegister
import com.example.filmmovie.utils.StoreUserData
import com.example.filmmovie.utils.showInvisible
import com.example.filmmovie.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentRegisterBinding

    @Inject
    lateinit var userData: StoreUserData

    @Inject
    lateinit var body: BodyRegister

    //Other
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Click
            submitBtn.setOnClickListener { view ->
                val name = nameEdt.text.toString()
                val email = emailEdt.text.toString()
                val password = passwordEdt.text.toString()
                //Validation
                if (name.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()) {
                    body.name = name
                    body.email = email
                    body.password = password
                } else {
                    Snackbar.make(view, getString(R.string.fillAllFields), Snackbar.LENGTH_SHORT).show()
                }
                //Send data
                viewModel.sendRegisterUser(body)
                //Loading
                viewModel.loading.observe(viewLifecycleOwner) { isShown ->
                    if (isShown) {
                        submitLoading.showInvisible(true)
                        submitBtn.showInvisible(false)
                    } else {
                        submitLoading.showInvisible(false)
                        submitBtn.showInvisible(true)
                    }
                }
                //Register
                viewModel.registerUser.observe(viewLifecycleOwner) { response ->
                    lifecycle.coroutineScope.launchWhenCreated {
                        userData.saveUserToken(response.name.toString())
                    }
                }
            }
        }
    }
}