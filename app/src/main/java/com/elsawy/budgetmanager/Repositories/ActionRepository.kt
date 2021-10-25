package com.elsawy.budgetmanager.Repositories

import com.elsawy.budgetmanager.data.local.Action

interface ActionRepository {

    suspend fun insertActionItem(action: Action)

    suspend fun getBalance()

}