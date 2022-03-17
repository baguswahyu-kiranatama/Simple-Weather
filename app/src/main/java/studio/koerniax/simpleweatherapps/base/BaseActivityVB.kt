package studio.koerniax.simpleweatherapps.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by KOERNIAX at 16/03/22
 */
abstract class BaseActivityVB<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)
    protected abstract val bindingInflater: (LayoutInflater) -> VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}