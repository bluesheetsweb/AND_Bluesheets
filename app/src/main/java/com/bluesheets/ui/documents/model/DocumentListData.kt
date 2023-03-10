package com.bluesheets.ui.documents.model

data class DocumentListData(
    var id: Int,
    var userId: Int,
    var filename: String = "",
    var exportPreviewUrl: String = "",
    var statusExport: String = "")
