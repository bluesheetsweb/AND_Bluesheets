package src.wrapperutil.utilities

import androidx.annotation.IntDef
import src.wrapperutil.utilities.WrapperConstant.ALIGN_CENTER
import src.wrapperutil.utilities.WrapperConstant.ALIGN_LEFT
import src.wrapperutil.utilities.WrapperConstant.ALIGN_RIGHT
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_BLACK
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_BLACK_TRANSPARENT_80
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_ERROR
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_GRAY_25
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_GRAY_50
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_GRAY_75
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_GRAY_LIGHT
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_SECONDARY
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_SEPERATOR_COLOR
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_TRANSPARENT
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_TRANSPARENT_BLACK
import src.wrapperutil.utilities.WrapperConstant.BACKGROUND_WHITE
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_DISABLED
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_GRAY_75
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_GRAY_FILLED_ENABLE
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_PRIMARY
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_PRIMARY_BLANK
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_PRIMARY_STROKED_ONLY
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_SECONDARY
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_SECONDARY_BLANK
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_SECONDARY_STROKED_ONLY
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_STROKE_ONLY
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_TEXT_DISABLED
import src.wrapperutil.utilities.WrapperConstant.BUTTON_MODE_WHITE_TEXT_STROKE_ONLY
import src.wrapperutil.utilities.WrapperConstant.CORNER_ALL
import src.wrapperutil.utilities.WrapperConstant.CORNER_ONLY_LEFT
import src.wrapperutil.utilities.WrapperConstant.CORNER_ONLY_RIGHT
import src.wrapperutil.utilities.WrapperConstant.CORNER_SKIP_BOTTOM_RIGHT
import src.wrapperutil.utilities.WrapperConstant.CORNER_SKIP_TOP_LEFT
import src.wrapperutil.utilities.WrapperConstant.CORNER_SKIP_TOP_RIGHT
import src.wrapperutil.utilities.WrapperConstant.DRAWABLE_BOTTOM
import src.wrapperutil.utilities.WrapperConstant.DRAWABLE_LEFT
import src.wrapperutil.utilities.WrapperConstant.DRAWABLE_RIGHT
import src.wrapperutil.utilities.WrapperConstant.DRAWABLE_TOP
import src.wrapperutil.utilities.WrapperConstant.EDIT_MODE_ERROR
import src.wrapperutil.utilities.WrapperConstant.EDIT_MODE_LARGE
import src.wrapperutil.utilities.WrapperConstant.EDIT_MODE_MEDIUM
import src.wrapperutil.utilities.WrapperConstant.EDIT_MODE_SMALL
import src.wrapperutil.utilities.WrapperConstant.FLOATING_TYPE_CIRCLE
import src.wrapperutil.utilities.WrapperConstant.FLOATING_TYPE_RECTANGLE
import src.wrapperutil.utilities.WrapperConstant.GRAVITY_CENTER
import src.wrapperutil.utilities.WrapperConstant.GRAVITY_LEFT
import src.wrapperutil.utilities.WrapperConstant.GRAVITY_RIGHT
import src.wrapperutil.utilities.WrapperConstant.INPUT_TYPE_EMAIL
import src.wrapperutil.utilities.WrapperConstant.INPUT_TYPE_NUMBER
import src.wrapperutil.utilities.WrapperConstant.INPUT_TYPE_TEXT
import src.wrapperutil.utilities.WrapperConstant.REQUEST_TYPE_GET
import src.wrapperutil.utilities.WrapperConstant.REQUEST_TYPE_POST
import src.wrapperutil.utilities.WrapperConstant.SEPARATOR_MODE_LARGE
import src.wrapperutil.utilities.WrapperConstant.SEPARATOR_MODE_MEDIUM
import src.wrapperutil.utilities.WrapperConstant.SEPARATOR_MODE_NORMAL
import src.wrapperutil.utilities.WrapperConstant.SEPARATOR_MODE_TINY
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_ERROR
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_EXIT_APP
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_LOADING_BOTTOM
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_LOADING_TOP
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_NODATA
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_NONE
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_NO_INTERNET_TOAST
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_READ_ONLY_DIALOG
import src.wrapperutil.utilities.WrapperConstant.STATE_SCREEN_SUCCESS
import src.wrapperutil.utilities.WrapperConstant.TAG_MODE_GREY
import src.wrapperutil.utilities.WrapperConstant.TAG_MODE_GREY_50
import src.wrapperutil.utilities.WrapperConstant.TAG_MODE_GREY_75
import src.wrapperutil.utilities.WrapperConstant.TAG_MODE_WHITE
import src.wrapperutil.utilities.WrapperConstant.TAG_MODE_YELLOW
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_HEADING_MEDIUM
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_PARAGRAPH_BOLD
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_ERROR
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_GRAY_75
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_SECONDARY
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_WHITE
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR_GRAY_50
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SMALL_MEDIUM_GRAY_75
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SUB_HEADING_BOLD
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_GRAY_75
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_SECONDARY
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SUB_HEADING_MEDIUM_WHITE
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR
import src.wrapperutil.utilities.WrapperConstant.TEXT_MODE_SUB_HEADING_REGULAR_GRAY_50
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class WrapperEnumAnnotation(var state: Int) {

    @IntDef(WrapperConstant.STATE_SCREEN_LOADING, STATE_SCREEN_SUCCESS, STATE_SCREEN_EXIT_APP, STATE_SCREEN_READ_ONLY_DIALOG, STATE_SCREEN_ERROR, STATE_SCREEN_LOADING_BOTTOM, STATE_SCREEN_LOADING_TOP)
    @Retention(RetentionPolicy.SOURCE)
    annotation class BaseState

    @IntDef(GRAVITY_CENTER, GRAVITY_LEFT, GRAVITY_RIGHT)
    @Retention(RetentionPolicy.SOURCE)
    annotation class GravityMode

    @IntDef(FLOATING_TYPE_CIRCLE, FLOATING_TYPE_RECTANGLE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class FloatingMode

    @IntDef(SEPARATOR_MODE_LARGE, SEPARATOR_MODE_TINY, SEPARATOR_MODE_NORMAL, SEPARATOR_MODE_MEDIUM)
    @Retention(RetentionPolicy.SOURCE)
    annotation class SeparatorMode

    @IntDef(REQUEST_TYPE_GET, REQUEST_TYPE_POST)
    @Retention(RetentionPolicy.SOURCE)
    annotation class RequestType

    @IntDef(TAG_MODE_GREY, TAG_MODE_WHITE, TAG_MODE_GREY_75, TAG_MODE_YELLOW, TAG_MODE_GREY_50)
    @Retention(RetentionPolicy.SOURCE)
    annotation class TagMode

    @IntDef(
        BUTTON_MODE_PRIMARY, BUTTON_MODE_SECONDARY, BUTTON_MODE_DISABLED, BUTTON_MODE_PRIMARY_STROKED_ONLY,
        BUTTON_MODE_SECONDARY_STROKED_ONLY, BUTTON_MODE_PRIMARY_BLANK, BUTTON_MODE_SECONDARY_BLANK,
        BUTTON_MODE_STROKE_ONLY, BUTTON_MODE_TEXT_DISABLED, BUTTON_MODE_WHITE_TEXT_STROKE_ONLY, BUTTON_MODE_GRAY_75, BUTTON_MODE_GRAY_FILLED_ENABLE
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class ButtonMode

    @IntDef(
        TEXT_MODE_HEADING_MEDIUM, TEXT_MODE_SUB_HEADING_BOLD, TEXT_MODE_SUB_HEADING_REGULAR, TEXT_MODE_SUB_HEADING_MEDIUM_WHITE,
        TEXT_MODE_SUB_HEADING_MEDIUM_GRAY_75, TEXT_MODE_SUB_HEADING_MEDIUM_SECONDARY, TEXT_MODE_SUB_HEADING_REGULAR_GRAY_50,
        TEXT_MODE_PARAGRAPH_BOLD, TEXT_MODE_PARAGRAPH_REGULAR_GRAY_50, TEXT_MODE_PARAGRAPH_MEDIUM_GRAY_75, TEXT_MODE_PARAGRAPH_MEDIUM_WHITE,
        TEXT_MODE_PARAGRAPH_MEDIUM_SECONDARY, TEXT_MODE_PARAGRAPH_MEDIUM_ERROR, TEXT_MODE_SMALL_MEDIUM_GRAY_75
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class TextMode

    @IntDef(EDIT_MODE_LARGE, EDIT_MODE_MEDIUM, EDIT_MODE_SMALL, EDIT_MODE_ERROR)
    @Retention(RetentionPolicy.SOURCE)
    annotation class EditMode

    @IntDef(ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT)
    @Retention(RetentionPolicy.SOURCE)
    annotation class ViewAlignment

    @IntDef(DRAWABLE_LEFT, DRAWABLE_RIGHT, DRAWABLE_TOP, DRAWABLE_BOTTOM)
    @Retention(RetentionPolicy.SOURCE)
    annotation class ViewDrawable

    @IntDef(WrapperConstant.STATE_SCREEN_ERROR, STATE_SCREEN_NODATA, WrapperConstant.STATE_SCREEN_LOADING, STATE_SCREEN_NONE, WrapperConstant.STATE_SCREEN_SUCCESS, STATE_SCREEN_NO_INTERNET_TOAST)
    @Retention(RetentionPolicy.SOURCE)
    annotation class StateMode

    @IntDef(INPUT_TYPE_TEXT, INPUT_TYPE_NUMBER, INPUT_TYPE_EMAIL)
    @Retention(RetentionPolicy.SOURCE)
    annotation class InputType

    @IntDef(CORNER_ALL, CORNER_SKIP_TOP_LEFT, CORNER_SKIP_TOP_RIGHT, CORNER_SKIP_BOTTOM_RIGHT, CORNER_ONLY_RIGHT, CORNER_ONLY_LEFT)
    @Retention(RetentionPolicy.SOURCE)
    annotation class CornerType

    @IntDef(
        BACKGROUND_WHITE, BACKGROUND_SECONDARY, BACKGROUND_ERROR, BACKGROUND_BLACK, BACKGROUND_GRAY_75, BACKGROUND_GRAY_50, BACKGROUND_GRAY_25, BACKGROUND_GRAY_LIGHT, BACKGROUND_TRANSPARENT_BLACK,
        BACKGROUND_TRANSPARENT, BACKGROUND_BLACK_TRANSPARENT_80, BACKGROUND_SEPERATOR_COLOR
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class BackgroundType
}
