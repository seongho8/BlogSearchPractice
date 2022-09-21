package com.sh.search

import org.junit.jupiter.params.provider.ArgumentsSource

@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(AnnotationRetention.RUNTIME)
@ArgumentsSource(DomainArgumentsProvider::class)
annotation class DomainArgumentsSource
