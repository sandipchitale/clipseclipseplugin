# Clips Eclipse plug-in #

## Update Site ##

http://clipseclipseplugin.googlecode.com/svn/trunk/ClipsUpdateSite/

## Description ##

This plug-in supports cut/copy/paste and management of multiple snippets of text.

  * Commands
    * Copy text selected in text editor as a clip `Alt+Shift+X Alt+Shift+C`
    * Cut text selected in text editor as a clip `Alt+Shift+X Alt+Shift+X`
    * Copy Clipboard text content as a clip `Alt+Shift+X Alt+Shift+B`
    * Paste clip in active text editor `Alt+Shift+X Alt+Shift+V`. The pasted text is left selected. If this command is invoked again within ~1 sec the successive clips are pasted.
    * Paste clip from a popup
    * Rotate Clips Up `Alt+Shift+X Alt+Shift+9`
    * Rotate Clips Down `Alt+Shift+X Alt+Shift+0`

  * Clips Popup

![http://clipseclipseplugin.googlecode.com/files/ClipsPopup.png](http://clipseclipseplugin.googlecode.com/files/ClipsPopup.png)

  * Clips View
    * Supports filtering of clips using wildcards

![http://clipseclipseplugin.googlecode.com/files/ClipsView.png](http://clipseclipseplugin.googlecode.com/files/ClipsView.png)

  * Clips View Actions
    * Paste
      * If there is no selection the first clip is pasted. If this action is invoked again within ~1 sec the successive clips are pasted.
      * If there is (multiple) selection, the selected clip(s) are pasted.
    * Rotate Clips Up
    * Rotate Clips Down
    * Move Clip Up
    * Move Clip Down
    * Edit clip
    * Remove selected clips
    * Remove All clips
  * Clips preferences page
    * Automatically create clips after Cut or Copy commands
    * Number of clips to store

![http://clipseclipseplugin.googlecode.com/files/ClipsPreferencesPage.png](http://clipseclipseplugin.googlecode.com/files/ClipsPreferencesPage.png)