package com.littleboysoftware.hiltintroduction

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@AndroidEntryPoint // i.e. this is able to have dependencies injected into it
class MainActivity : AppCompatActivity() {

    @Inject // i.e. this will be injected
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
    }
}

class SomeClass
@Inject // i.e. this is injectable/it is a dependency
constructor(
        private val someInterfaceImpl: SomeInterface,
        private val gsonConverter: Gson
){
    fun doAThing(): String{
        return "Look I got: ${someInterfaceImpl.getAThing()}"
    }
}

class SomeInterfaceImpl
@Inject // i.e. this is injectable/it is a dependency
constructor(
    private val something: String
): SomeInterface {
    override fun getAThing(): String {
        return something
    }

}

interface SomeInterface {
    abstract fun getAThing(): String
}

/* BINDS METHOD
@InstallIn(ActivityComponent::class) // what component (dependency holder) to put this module (dependency provider) in
@Module // something that knows how to produce dependencies
abstract class SomeInterfaceBindingModule {

    @ActivityScoped // what lifecycle scope to have, note that this must be <= the component scope above
    @Binds // this annotation is for binding a concrete implementation to an interface. Only works for classes that you own!
    abstract fun bindSomeInterfaceImplToSomeInterface(someInterfaceImpl: SomeInterfaceImpl): SomeInterface
}
*/

@InstallIn(ActivityComponent::class)
@Module
class SomeInterfaceProvider {
    @ActivityScoped
    @Provides
    fun provideSomeThing(): String {
        return "something"
    }

    @ActivityScoped
    @Provides
    fun provideSomeInterface(something: String): SomeInterface {
        return SomeInterfaceImpl(something)
    }
}

/* We must install into a component that lives >= as long as activity because that is what we are
 * injecting into. Also note that the component you choose for InstallIn forms an upper bound on the
 * scope that you can use for @Provides and @Binds. This is because the component can't guarantee it
 * will be alive when components above it in the heirarchy are e.g. ActivityComponent cannot
 * guarantee it will be alive when ApplicationComponent is, hence modules that are installed into
 * ActivitiyComponent cannot use scopes larger than @ActivityScoped.
 */
@InstallIn(ActivityComponent::class) // We can inject dependencies into Activities, Fragments, Views and anything else below that in the heirarchy. We cannot install into ViewModels or Applications
@Module
class NetworkProviderModule {

    @ActivityScoped // The largest scope we can use it activity scope because our component (dependency container) is only guaranteed to live that long
    @Provides
    fun gsonConverterProvider(): Gson {
        return Gson()
    }
}



/* FIRST LESSONS
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // field injection. The field must be a public var because it needs to be accessible and writable by Hilt
    @Inject
    lateinit var someDependency: SomeDependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someDependency.doAThing())
        println(someDependency.doAnotherThing())
    }
}

@AndroidEntryPoint
class MyFragment: Fragment() {
    @Inject
    lateinit var someDependency: SomeDependency
}

@ActivityScoped
class SomeDependency @Inject constructor(
        // constructor injection, field can be private and read only because it is set on creation, doesn't need to be accessible/writable by Hilt afterwards
        private val someOtherDependency: SomeOtherDependency
) {
    fun doAThing(): String {
        return "Look, i did a thing!"
    }
    fun doAnotherThing() = someOtherDependency.doAnotherThing()
}

class SomeOtherDependency @Inject constructor() {
    fun doAnotherThing(): String {
        return "Look, i did some other thing!"
    }
}
*/