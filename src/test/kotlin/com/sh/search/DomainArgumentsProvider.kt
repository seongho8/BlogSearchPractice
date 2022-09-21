package com.sh.search

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.AnnotationConsumer
import java.util.*
import java.util.stream.Stream

class DomainArgumentsProvider : ArgumentsProvider, AnnotationConsumer<DomainArgumentsSource> {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        val method = context.requiredTestMethod
        val parameterTypes = method.parameterTypes

        val arguments = arrayOfNulls<Any>(parameterTypes.size)
        val argumentResolver: DomainArgumentResolver = DomainArgumentResolver.instance
        for (i in arguments.indices) {
            val argument: Any? = argumentResolver.tryResolve(parameterTypes[i])
            arguments[i] = argument
        }

        return Arrays.stream(arrayOf(Arguments.of(*arguments)))
    }

    override fun accept(t: DomainArgumentsSource) {
    }
}