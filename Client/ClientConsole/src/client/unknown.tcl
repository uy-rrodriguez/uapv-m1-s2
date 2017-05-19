#############################################################################
# Generated by PAGE version 4.8.6
# in conjunction with Tcl version 8.6
#    Nov 22, 2016 01:24:43 AM
#
set vTcl(timestamp) ""




#############################################################################
## vTcl Code to Load Stock Images


if {![info exist vTcl(sourcing)]} {
#############################################################################
## Procedure:  vTcl:rename

proc ::vTcl:rename {name} {
    ## This procedure may be used free of restrictions.
    ##    Exception added by Christian Gavin on 08/08/02.
    ## Other packages and widget toolkits have different licensing requirements.
    ##    Please read their license agreements for details.
    regsub -all "\\." $name "_" ret
    regsub -all "\\-" $ret "_" ret
    regsub -all " " $ret "_" ret
    regsub -all "/" $ret "__" ret
    regsub -all "::" $ret "__" ret

    return [string tolower $ret]
}

#############################################################################
## Procedure:  vTcl:image:create_new_image

proc ::vTcl:image:create_new_image {filename {description {no description}} {type {}} {data {}}} {
    ## This procedure may be used free of restrictions.
    ##    Exception added by Christian Gavin on 08/08/02.
    ## Other packages and widget toolkits have different licensing requirements.
    ##    Please read their license agreements for details.
    # Does the image already exist?
    if {[info exists ::vTcl(images,files)]} {
        if {[lsearch -exact $::vTcl(images,files) $filename] > -1} { return }
    }
    if {![info exists ::vTcl(sourcing)] && [string length $data] > 0} {
        set object [image create  [vTcl:image:get_creation_type $filename]  -data $data]
    } else {
        # Wait a minute... Does the file actually exist?
        if {! [file exists $filename] } {
            # Try current directory
            set script [file dirname [info script]]
            set filename [file join $script [file tail $filename] ]
        }

        if {![file exists $filename]} {
            set description "file not found!"
            ## will add 'broken image' again when img is fixed, for
            ## now create empty
            set object [image create photo -width 1 -height 1]
        } else {
            set object [image create  [vTcl:image:get_creation_type $filename]  -file $filename]
        }
    }

    set reference [vTcl:rename $filename]
    set ::vTcl(images,$reference,image)       $object
    set ::vTcl(images,$reference,description) $description
    set ::vTcl(images,$reference,type)        $type
    set ::vTcl(images,filename,$object)       $filename

    lappend ::vTcl(images,files) $filename
    lappend ::vTcl(images,$type) $object
    set ::vTcl(imagefile,$object) $filename   ;# Rozen
    # return image name in case caller might want it
    return $object
}

#############################################################################
## Procedure:  vTcl:image:get_image

proc ::vTcl:image:get_image {filename} {
    ## This procedure may be used free of restrictions.
    ##    Exception added by Christian Gavin on 08/08/02.
    ## Other packages and widget toolkits have different licensing requirements.
    ##    Please read their license agreements for details.

    set reference [vTcl:rename $filename]

    # Let's do some checking first
    if {![info exists ::vTcl(images,$reference,image)]} {
        # Well, the path may be wrong; in that case check
        # only the filename instead, without the path.

        set imageTail [file tail $filename]

        foreach oneFile $::vTcl(images,files) {
            if {[file tail $oneFile] == $imageTail} {
                set reference [vTcl:rename $oneFile]
                break
            }
        }
    }
    # Rozen. There follows a hack in case one wants to rerun a tcl
    # file which contains a file name where an image is expected.
    if {![info exists ::vTcl(images,$reference,image)]} {
        set ::vTcl(images,$reference,image)  [vTcl:image:create_new_image $filename]
    }
    return $::vTcl(images,$reference,image)
}

#############################################################################
## Procedure:  vTcl:image:get_creation_type

proc ::vTcl:image:get_creation_type {filename} {
    ## This procedure may be used free of restrictions.
    ##    Exception added by Christian Gavin on 08/08/02.
    ## Other packages and widget toolkits have different licensing requirements.
    ##    Please read their license agreements for details.

    switch [string tolower [file extension $filename]] {
        .ppm -
        .jpg -
        .bmp -
        .gif    {return photo}
        .xbm    {return bitmap}
        default {return photo}
    }
}

foreach img {


            } {
    eval set _file [lindex $img 0]
    vTcl:image:create_new_image\
        $_file [lindex $img 1] [lindex $img 2] [lindex $img 3]
}

}
#############################################################################
## vTcl Code to Load User Images

catch {package require Img}

foreach img {

        {{$[pwd]/img/play.png} {user image} user {}}
        {{$[pwd]/img/list.png} {user image} user {}}
        {{$[pwd]/img/search.png} {user image} user {}}
        {{$[pwd]/img/upload.png} {user image} user {}}
        {{$[pwd]/img/power.png} {user image} user {}}
        {{$[pwd]/img/logo.png} {user image} user {}}

            } {
    eval set _file [lindex $img 0]
    vTcl:image:create_new_image\
        $_file [lindex $img 1] [lindex $img 2] [lindex $img 3]
}

set vTcl(actual_gui_bg) #d9d9d9
set vTcl(actual_gui_fg) #000000
set vTcl(actual_gui_menu_bg) #d9d9d9
set vTcl(actual_gui_menu_fg) #000000
set vTcl(complement_color) #d9d9d9
set vTcl(analog_color_p) #d9d9d9
set vTcl(analog_color_m) #d9d9d9
set vTcl(active_fg) #000000
set vTcl(actual_gui_menu_active_bg)  #d8d8d8
set vTcl(active_menu_fg) #000000
#################################
#LIBRARY PROCEDURES
#


if {[info exists vTcl(sourcing)]} {

proc vTcl:project:info {} {
    set base .top37
    namespace eval ::widgets::$base {
        set dflt,origin 0
        set runvisible 1
    }
    set site_3_0 $base.fra44
    namespace eval ::widgets_bindings {
        set tagslist _TopLevel
    }
    namespace eval ::vTcl::modules::main {
        set procs {
        }
        set compounds {
        }
        set projectType single
    }
}
}

#################################
# USER DEFINED PROCEDURES
#

#################################
# GENERATED GUI PROCEDURES
#

proc vTclWindow.top37 {base} {
    if {$base == ""} {
        set base .top37
    }
    if {[winfo exists $base]} {
        wm deiconify $base; return
    }
    set top $base
    ###################
    # CREATING WIDGETS
    ###################
    vTcl::widgets::core::toplevel::createCmd $top -class Toplevel \
        -background {#d9d9d9} 
    wm focusmodel $top passive
    wm geometry $top 435x552+508+95
    update
    # set in toplevel.wgt.
    global vTcl
    set vTcl(save,dflt,origin) 0
    wm maxsize $top 1362 741
    wm minsize $top 120 1
    wm overrideredirect $top 0
    wm resizable $top 1 1
    wm deiconify $top
    wm title $top "New Toplevel 1"
    vTcl:DefineAlias "$top" "Toplevel1" vTcl:Toplevel:WidgetProc "" 1
    frame $top.fra44 \
        -borderwidth 2 -relief groove -background {#d9d9d9} -height 145 \
        -width 215 
    vTcl:DefineAlias "$top.fra44" "Frame1" vTcl:WidgetProc "Toplevel1" 1
    set site_3_0 $top.fra44
    button $site_3_0.cpd46 \
        -activebackground {#d9d9d9} -activeforeground {#000000} \
        -background {#d9d9d9} -disabledforeground {#a3a3a3} \
        -foreground {#000000} -highlightbackground {#d9d9d9} \
        -highlightcolor black \
        -image [vTcl:image:get_image [file join G:/ Dev RepoGit uapv-m1-s1 ProjetMP3 src client img power.png]] \
        -pady 0 -text Button 
    vTcl:DefineAlias "$site_3_0.cpd46" "Button5" vTcl:WidgetProc "Toplevel1" 1
    button $site_3_0.cpd47 \
        -activebackground {#d9d9d9} -activeforeground {#000000} \
        -background {#d9d9d9} -disabledforeground {#a3a3a3} \
        -foreground {#000000} -highlightbackground {#d9d9d9} \
        -highlightcolor black \
        -image [vTcl:image:get_image [file join G:/ Dev RepoGit uapv-m1-s1 ProjetMP3 src client img logo.png]] \
        -pady 0 -text Button 
    vTcl:DefineAlias "$site_3_0.cpd47" "Button6" vTcl:WidgetProc "Toplevel1" 1
    button $site_3_0.cpd48 \
        -activebackground {#d9d9d9} -activeforeground {#000000} \
        -background {#d9d9d9} -disabledforeground {#a3a3a3} \
        -foreground {#000000} -highlightbackground {#d9d9d9} \
        -highlightcolor black \
        -image [vTcl:image:get_image [file join G:/ Dev RepoGit uapv-m1-s1 ProjetMP3 src client img upload.png]] \
        -pady 0 -text Button 
    vTcl:DefineAlias "$site_3_0.cpd48" "Button4" vTcl:WidgetProc "Toplevel1" 1
    button $site_3_0.cpd50 \
        -activebackground {#d9d9d9} -activeforeground {#000000} \
        -background {#d9d9d9} -disabledforeground {#a3a3a3} \
        -foreground {#000000} -highlightbackground {#d9d9d9} \
        -highlightcolor black \
        -image [vTcl:image:get_image [file join G:/ Dev RepoGit uapv-m1-s1 ProjetMP3 src client img list.png]] \
        -pady 0 -text Button 
    vTcl:DefineAlias "$site_3_0.cpd50" "Button2" vTcl:WidgetProc "Toplevel1" 1
    button $site_3_0.cpd53 \
        -activebackground {#d9d9d9} -activeforeground {#000000} \
        -background {#d9d9d9} -disabledforeground {#a3a3a3} \
        -foreground {#000000} -highlightbackground {#d9d9d9} \
        -highlightcolor black \
        -image [vTcl:image:get_image [file join G:/ Dev RepoGit uapv-m1-s1 ProjetMP3 src client img play.png]] \
        -pady 0 -text Button 
    vTcl:DefineAlias "$site_3_0.cpd53" "Button3" vTcl:WidgetProc "Toplevel1" 1
    button $site_3_0.cpd54 \
        -activebackground {#d9d9d9} -activeforeground {#000000} \
        -background {#d9d9d9} -disabledforeground {#a3a3a3} \
        -foreground {#000000} -highlightbackground {#d9d9d9} \
        -highlightcolor black \
        -image [vTcl:image:get_image [file join G:/ Dev RepoGit uapv-m1-s1 ProjetMP3 src client img search.png]] \
        -pady 0 -text Button 
    vTcl:DefineAlias "$site_3_0.cpd54" "Button7" vTcl:WidgetProc "Toplevel1" 1
    place $site_3_0.cpd46 \
        -in $site_3_0 -x 140 -y 70 -width 70 -height 70 -anchor nw \
        -bordermode inside 
    place $site_3_0.cpd47 \
        -in $site_3_0 -x 70 -y 70 -width 70 -height 70 -anchor nw \
        -bordermode inside 
    place $site_3_0.cpd48 \
        -in $site_3_0 -x 2 -y 72 -width 70 -height 70 -anchor nw \
        -bordermode ignore 
    place $site_3_0.cpd50 \
        -in $site_3_0 -x 70 -y 0 -width 70 -height 70 -anchor nw \
        -bordermode inside 
    place $site_3_0.cpd53 \
        -in $site_3_0 -x 0 -y 0 -width 70 -height 70 -anchor nw \
        -bordermode inside 
    place $site_3_0.cpd54 \
        -in $site_3_0 -x 142 -y 2 -width 70 -height 70 -anchor nw \
        -bordermode ignore 
    ###################
    # SETTING GEOMETRY
    ###################
    place $top.fra44 \
        -in $top -x 119 -y 183 -width 215 -relwidth 0 -height 145 \
        -relheight 0 -anchor nw -bordermode ignore 

    vTcl:FireEvent $base <<Ready>>
}

#############################################################################
## Binding tag:  _TopLevel

bind "_TopLevel" <<Create>> {
    if {![info exists _topcount]} {set _topcount 0}; incr _topcount
}
bind "_TopLevel" <<DeleteWindow>> {
    if {[set ::%W::_modal]} {
                vTcl:Toplevel:WidgetProc %W endmodal
            } else {
                destroy %W; if {$_topcount == 0} {exit}
            }
}
bind "_TopLevel" <Destroy> {
    if {[winfo toplevel %W] == "%W"} {incr _topcount -1}
}

Window show .
Window show .top37
