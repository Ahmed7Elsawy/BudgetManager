package com.elsawy.budgetmanager.Repositories

import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.ActionDao
import com.elsawy.budgetmanager.data.local.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor(
    private val actionDao: ActionDao,
) : ActionRepository {

    override val allActions: Flow<List<Action>> = actionDao.getAllActions()

    override suspend fun insertActionItem(action: Action) {
        actionDao.insertAction(action)
    }

    override suspend fun getActionsByCategory(category: Category): Flow<List<Action>> = actionDao.getActionsByCategory(category)


}