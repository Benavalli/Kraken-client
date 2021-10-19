package com.example.krakenclient.ui.features.switches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.krakenclient.databinding.FragmentSwitchesBinding
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel

class SwitchesFragment: Fragment() {

    private lateinit var binding: FragmentSwitchesBinding
    private val viewModel by viewModel<SwitchesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwitchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lightRelayObserver = Observer<Boolean> { setupLightSwitch(it) }
        val pumpRelayObserver = Observer<Boolean> { setupPumpSwitch(it) }
        val humidifierRelayObserver = Observer<Boolean> { setupHumidifierSwitch(it) }
        val exhaustRelayObserver = Observer<Boolean> { setupExhaustSwitch(it) }

        viewModel.lightRelay.observe(viewLifecycleOwner, lightRelayObserver)
        viewModel.pumpRelay.observe(viewLifecycleOwner, pumpRelayObserver)
        viewModel.humidifierRelay.observe(viewLifecycleOwner, humidifierRelayObserver)
        viewModel.exhaustRelay.observe(viewLifecycleOwner, exhaustRelayObserver)

        viewModel.getRelays()
    }

    private fun setupLightSwitch(enabled: Boolean) {
        binding.lightSwitch.setOnCheckedChangeListener(null)
        binding.lightSwitch.isChecked = enabled
        binding.lightSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeLightRelayState(isChecked)
        }
    }

    private fun setupPumpSwitch(enabled: Boolean) {
        binding.pumpSwitch.setOnCheckedChangeListener(null)
        binding.pumpSwitch.isChecked = enabled
        binding.pumpSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changePumpRelayState(isChecked)
        }
    }

    private fun setupHumidifierSwitch(enabled: Boolean) {
        binding.humidifierSwitch.setOnCheckedChangeListener(null)
        binding.humidifierSwitch.isChecked = enabled
        binding.humidifierSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeHumidifierRelayState(isChecked)
        }
    }

    private fun setupExhaustSwitch(enabled: Boolean) {
        binding.exhaustSwitch.setOnCheckedChangeListener(null)
        binding.exhaustSwitch.isChecked = enabled
        binding.exhaustSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeExhaustRelayState(isChecked)
        }
    }
}
