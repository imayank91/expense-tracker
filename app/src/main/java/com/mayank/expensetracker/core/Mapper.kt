package com.mayank.expensetracker.core

interface Mapper<P, R> {
    fun map(from: P): R
}