package com.lib.common.ext

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.lib.common.base.BaseActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified B : ViewBinding> BaseActivity.binding() = lazy {
    inflateBinding<B>(layoutInflater).apply {
        mBaseBinding.mBaseContainer.addView(root)
    }
}

inline fun <reified B : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<B>(layoutInflater).apply {
        setContentView(root)
    }
}

inline fun <reified B : ViewBinding> binding() = FragmentBindProperty(B::class.java)

inline fun <reified B : ViewBinding> inflateBinding(inflater: LayoutInflater) =
    B::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as B

class FragmentBindProperty<B : ViewBinding>(private val clazz: Class<B>) :
    ReadOnlyProperty<Fragment, B> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): B =
        try {
            thisRef.requireView().getBinding(clazz).also { binding ->
                if (binding is ViewDataBinding) binding.lifecycleOwner = thisRef.viewLifecycleOwner
            }
        } catch (e: IllegalStateException) {
            throw IllegalStateException()
        }
}

fun <B : ViewBinding> View.getBinding(clazz: Class<B>) =
    bindingCache as? B ?: (clazz.getMethod("bind", View::class.java)
        .invoke(null, this) as B)
        .also { binding -> bindingCache = binding }

private var View.bindingCache: Any?
    get() = getTag(Int.MIN_VALUE)
    set(value) = setTag(Int.MIN_VALUE, value)