package com.elsawy.budgetmanager.Repositories

import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.ActionDao
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor(
    private val actionDao: ActionDao
): ActionRepository {
    override suspend fun insertActionItem(action: Action) {
        actionDao.insertAction(action)
    }

    override suspend fun getBalance() {
        actionDao.getBalance()
    }

}