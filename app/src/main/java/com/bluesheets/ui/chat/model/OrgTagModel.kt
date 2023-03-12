package com.bluesheets.ui.chat.model

data class OrgTagModel( var id: Int?,
                        var name: String?,
                        var userDocumentTypes: MutableList<TagsModel>?)

data class TagsModel( var id: Int?,
                        var name: String?)