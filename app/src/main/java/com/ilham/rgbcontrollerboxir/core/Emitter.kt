package com.ilham.rgbcontrollerboxir.core

interface Emitter {
    fun emit(pattern : IntArray)
    fun hasIrEmitter() : Boolean
}