package com.ilham.rgbcontrollerboxir.core

import android.content.Context
import android.hardware.ConsumerIrManager

class EmitterImpl(context: Context) : Emitter {

    private val mCIR = context.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

    override fun emit(pattern: IntArray) {
        mCIR.transmit(Frequency.IR_FREQUENCY, pattern)
    }

    override fun hasIrEmitter(): Boolean {
        return mCIR.hasIrEmitter()
    }


}