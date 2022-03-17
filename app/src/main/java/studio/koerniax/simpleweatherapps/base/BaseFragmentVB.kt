package studio.koerniax.simpleweatherapps.base

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by KOERNIAX at 16/03/22
 */
abstract class BaseFragmentVB<VB : ViewBinding> : Fragment() {

    private var requestCode: Int = -1
    private lateinit var activityLauncher: ActivityResultLauncher<Intent>
    private lateinit var multiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                setOnActivityResult(requestCode, result)
                this.requestCode = -1
            }

        multiplePermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                setOnMultiplePermissionsRequest(requestCode, result)
                this.requestCode = -1
            }
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = (binding.root.background as? ColorDrawable)?.color
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true)
        color ?: run {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    typedValue.resourceId
                )
            )
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setOnActivityResult(requestCode: Int, result: ActivityResult) {}

    fun launchActivityForResult(requestCode: Int = -1, intent: Intent) {
        this.requestCode = requestCode
        activityLauncher.launch(intent)
    }

    open fun setOnMultiplePermissionsRequest(requestCode: Int, result: Map<String, Boolean>) {}

    fun launchMultiplePermissionsRequest(requestCode: Int = -1, permissions: Array<String>) {
        this.requestCode = requestCode
        multiplePermissionsLauncher.launch(permissions)
    }

    fun fragmentTag(): String = this::class.java.simpleName

}