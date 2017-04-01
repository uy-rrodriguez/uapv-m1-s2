<?php
//class queFaireAujourdhui {
function queFaireAujourdhui($meteo_belle,$mer_chaude,$presence_requins) {
  if ($meteo_belle) {
    if ($mer_chaude) {
      if ($presence_requins) {
        return('Plage:Nage');
      } else {
        return('Plage:Bronze');
      }
    } else {
      return('Plage:Bronze');
    }
  } else {
     return 'Lit:?';
  }
}
//}
?>