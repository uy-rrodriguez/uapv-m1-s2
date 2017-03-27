(Get-Host).UI.RawUI
$a = (Get-Host).UI.RawUI
$a.BackgroundColor = "white"
$a.ForegroundColor = "black"

$size = (Get-Host).UI.RawUI.WindowSize
$size.Width = 80
$size.Height = 30
(Get-Host).UI.RawUI.WindowSize = $size

$position = (Get-Host).UI.RawUI.Windowposition
$position.X = 0
$position.Y = 30
(Get-Host).UI.RawUI.Windowposition = $position