package com.github.biba.flashlang.firebase.db.connector

import com.github.biba.flashlang.db.IDbModel
import com.github.biba.lib.contracts.ICallback

interface IGetCallback<Element : IDbModel<String>> : ICallback<List<Element>>
