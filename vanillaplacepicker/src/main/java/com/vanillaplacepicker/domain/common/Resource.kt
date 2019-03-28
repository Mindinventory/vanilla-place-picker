package com.vanillaplacepicker.domain.common


class Resource<T>(val status: Status)  {

    var item: T? = null

    var throwable: Throwable? = null

    constructor(status: Status, item: T) : this(status) {
        this.item = item
    }

    constructor(status: Status, throwable: Throwable) : this(status) {
        this.throwable = throwable
    }
}
