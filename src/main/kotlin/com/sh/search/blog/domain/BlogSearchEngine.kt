package com.sh.search.blog.domain

import com.sh.search.core.PageableData

interface BlogSearchEngine {
    fun search(command: BlogSearchCommand) : PageableData<BlogContents>
}