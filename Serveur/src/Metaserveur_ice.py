# -*- coding: utf-8 -*-
# **********************************************************************
#
# Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
#
# This copy of Ice is licensed to you under the terms described in the
# ICE_LICENSE file included in this distribution.
#
# **********************************************************************
#
# Ice version 3.6.3
#
# <auto-generated>
#
# Generated from file `Metaserveur.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module AppMP3Player
_M_AppMP3Player = Ice.openModule('AppMP3Player')
__name__ = 'AppMP3Player'

if '_t_CommandeParams' not in _M_AppMP3Player.__dict__:
    _M_AppMP3Player._t_CommandeParams = IcePy.defineSequence('::AppMP3Player::CommandeParams', (), IcePy._t_string)

if 'Commande' not in _M_AppMP3Player.__dict__:
    _M_AppMP3Player.Commande = Ice.createTempClass()
    class Commande(object):
        """
        Cette structure représente une commande, avec ses paramètres d'entrée et de sortie.
        Members:
        commande -- Chaîne représentant la commande à exécuter.
        params -- Liste de paramètres pour la commande.
        L'ordre des paramètres doit être respecté afin que la commande
        puisse s'exécuter de manière correcte.
        erreur -- True s'il y a eu une erreur dans le traitement de la commande.
        msgErreur -- Message de l'erreur ou vide s'il n'y a pas eu d'erreur.
        retour -- Représentation en forme de string pour le retour.
        JSON pour des objets complexes, comme une liste de chansons.
        """
        def __init__(self, commande='', params=None, erreur=False, msgErreur='', retour=''):
            self.commande = commande
            self.params = params
            self.erreur = erreur
            self.msgErreur = msgErreur
            self.retour = retour

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.commande)
            if self.params:
                for _i0 in self.params:
                    _h = 5 * _h + Ice.getHash(_i0)
            _h = 5 * _h + Ice.getHash(self.erreur)
            _h = 5 * _h + Ice.getHash(self.msgErreur)
            _h = 5 * _h + Ice.getHash(self.retour)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_AppMP3Player.Commande):
                return NotImplemented
            else:
                if self.commande is None or other.commande is None:
                    if self.commande != other.commande:
                        return (-1 if self.commande is None else 1)
                else:
                    if self.commande < other.commande:
                        return -1
                    elif self.commande > other.commande:
                        return 1
                if self.params is None or other.params is None:
                    if self.params != other.params:
                        return (-1 if self.params is None else 1)
                else:
                    if self.params < other.params:
                        return -1
                    elif self.params > other.params:
                        return 1
                if self.erreur is None or other.erreur is None:
                    if self.erreur != other.erreur:
                        return (-1 if self.erreur is None else 1)
                else:
                    if self.erreur < other.erreur:
                        return -1
                    elif self.erreur > other.erreur:
                        return 1
                if self.msgErreur is None or other.msgErreur is None:
                    if self.msgErreur != other.msgErreur:
                        return (-1 if self.msgErreur is None else 1)
                else:
                    if self.msgErreur < other.msgErreur:
                        return -1
                    elif self.msgErreur > other.msgErreur:
                        return 1
                if self.retour is None or other.retour is None:
                    if self.retour != other.retour:
                        return (-1 if self.retour is None else 1)
                else:
                    if self.retour < other.retour:
                        return -1
                    elif self.retour > other.retour:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_AppMP3Player._t_Commande)

        __repr__ = __str__

    _M_AppMP3Player._t_Commande = IcePy.defineStruct('::AppMP3Player::Commande', Commande, (), (
        ('commande', (), IcePy._t_string),
        ('params', (), _M_AppMP3Player._t_CommandeParams),
        ('erreur', (), IcePy._t_bool),
        ('msgErreur', (), IcePy._t_string),
        ('retour', (), IcePy._t_string)
    ))

    _M_AppMP3Player.Commande = Commande
    del Commande

# End of module AppMP3Player

# Start of module AppMP3Player
__name__ = 'AppMP3Player'

if 'Metaserveur' not in _M_AppMP3Player.__dict__:
    _M_AppMP3Player.Metaserveur = Ice.createTempClass()
    class Metaserveur(Ice.Object):
        def __init__(self):
            if Ice.getType(self) == _M_AppMP3Player.Metaserveur:
                raise RuntimeError('AppMP3Player.Metaserveur is an abstract class')

        def ice_ids(self, current=None):
            return ('::AppMP3Player::Metaserveur', '::Ice::Object')

        def ice_id(self, current=None):
            return '::AppMP3Player::Metaserveur'

        def ice_staticId():
            return '::AppMP3Player::Metaserveur'
        ice_staticId = staticmethod(ice_staticId)

        def traiterCommande(self, ipClient, commande, current=None):
            pass

        def enregistrerServeur(self, ipServeur, current=None):
            pass

        def supprimerServeur(self, idServeur, current=None):
            pass

        def __str__(self):
            return IcePy.stringify(self, _M_AppMP3Player._t_Metaserveur)

        __repr__ = __str__

    _M_AppMP3Player.MetaserveurPrx = Ice.createTempClass()
    class MetaserveurPrx(Ice.ObjectPrx):

        def traiterCommande(self, ipClient, commande, _ctx=None):
            return _M_AppMP3Player.Metaserveur._op_traiterCommande.invoke(self, ((ipClient, commande), _ctx))

        def begin_traiterCommande(self, ipClient, commande, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_AppMP3Player.Metaserveur._op_traiterCommande.begin(self, ((ipClient, commande), _response, _ex, _sent, _ctx))

        def end_traiterCommande(self, _r):
            return _M_AppMP3Player.Metaserveur._op_traiterCommande.end(self, _r)

        def enregistrerServeur(self, ipServeur, _ctx=None):
            return _M_AppMP3Player.Metaserveur._op_enregistrerServeur.invoke(self, ((ipServeur, ), _ctx))

        def begin_enregistrerServeur(self, ipServeur, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_AppMP3Player.Metaserveur._op_enregistrerServeur.begin(self, ((ipServeur, ), _response, _ex, _sent, _ctx))

        def end_enregistrerServeur(self, _r):
            return _M_AppMP3Player.Metaserveur._op_enregistrerServeur.end(self, _r)

        def supprimerServeur(self, idServeur, _ctx=None):
            return _M_AppMP3Player.Metaserveur._op_supprimerServeur.invoke(self, ((idServeur, ), _ctx))

        def begin_supprimerServeur(self, idServeur, _response=None, _ex=None, _sent=None, _ctx=None):
            return _M_AppMP3Player.Metaserveur._op_supprimerServeur.begin(self, ((idServeur, ), _response, _ex, _sent, _ctx))

        def end_supprimerServeur(self, _r):
            return _M_AppMP3Player.Metaserveur._op_supprimerServeur.end(self, _r)

        def checkedCast(proxy, facetOrCtx=None, _ctx=None):
            return _M_AppMP3Player.MetaserveurPrx.ice_checkedCast(proxy, '::AppMP3Player::Metaserveur', facetOrCtx, _ctx)
        checkedCast = staticmethod(checkedCast)

        def uncheckedCast(proxy, facet=None):
            return _M_AppMP3Player.MetaserveurPrx.ice_uncheckedCast(proxy, facet)
        uncheckedCast = staticmethod(uncheckedCast)

        def ice_staticId():
            return '::AppMP3Player::Metaserveur'
        ice_staticId = staticmethod(ice_staticId)

    _M_AppMP3Player._t_MetaserveurPrx = IcePy.defineProxy('::AppMP3Player::Metaserveur', MetaserveurPrx)

    _M_AppMP3Player._t_Metaserveur = IcePy.defineClass('::AppMP3Player::Metaserveur', Metaserveur, -1, (), True, False, None, (), ())
    Metaserveur._ice_type = _M_AppMP3Player._t_Metaserveur

    Metaserveur._op_traiterCommande = IcePy.Operation('traiterCommande', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0), ((), _M_AppMP3Player._t_Commande, False, 0)), (), ((), _M_AppMP3Player._t_Commande, False, 0), ())
    Metaserveur._op_enregistrerServeur = IcePy.Operation('enregistrerServeur', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0),), (), ((), IcePy._t_int, False, 0), ())
    Metaserveur._op_supprimerServeur = IcePy.Operation('supprimerServeur', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_int, False, 0),), (), None, ())

    _M_AppMP3Player.Metaserveur = Metaserveur
    del Metaserveur

    _M_AppMP3Player.MetaserveurPrx = MetaserveurPrx
    del MetaserveurPrx

# End of module AppMP3Player
