package com.sh.search

class CompositeArgumentResolver(private vararg val resolvers: DomainArgumentResolver) : DomainArgumentResolver {

    override fun tryResolve(parameterType: Class<*>): Any? {
        for (resolver in resolvers) {
            val argument: Any? = resolver.tryResolve(parameterType)
            if (argument != null) {
                return argument
            }
        }
        return null
    }
}