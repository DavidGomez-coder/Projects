from gophish import Gophish
import argparse
from datetime import *
import dateutil
from dateutil import rrule
import time


#definicion de codigos para constantes (obtener resultados)
SENT_MAILS     = 'Email Sent'
OPEN_MAILS     = 'Clicked Link'
CLICKED_LINKS  = 'Clicked Link'
SUBMITTED_DATA = 'Submitted Data'


API_KEY = '3fa01a94caf435ca84d8b432b07f2f08892e99568a32f4997dd5dfac3f1a9f3e'
HOST    = 'https://localhost:33333'

argument_parser = argparse.ArgumentParser()
argument_parser.add_argument("-c", "--campaign", help="Identificador de la campaña de la que se quiere obtener datos")
args = argument_parser.parse_args()

api = Gophish(api_key= API_KEY, host= HOST, verify= False)

#funcion que convierte una fecha de tipo string a datetime
def stringToDate (string_date):
    return dateutil.parser.parse(string_date)

#funcion que devuelve una campaña asociada al ID pasado como parámetro 
# (retorna None si no se ha encontrado)
def campaignDefined (campId): 
    for c in api.campaigns.get():
        if str(c.id) == campId:
            return c
    return None


#funcion que devuelve una lista con los resultados de una campaña (devuelve los especificados en el segundo argumento)
def getResultData (timeline, type):
    data = []
    for t in timeline:
        if t.message == type:
            data.append(t)

    return data

#primera aparicion de un dato
def getOccurrence (data, type, order):
    list = data
    if order == 'LAST':
        list = data[::-1]
    
    for elem in list:
        if elem.message == type:
            return elem
    return None

#diferencia de dos fechas en dias, horas, minutos y segundos
def diffTimeString (d1, d2):
    dif = (d1-d2)
    days = dif.days
    seconds = dif.total_seconds()
    hour = round(seconds // 3600)
    seconds %= 3600
    minutes = round(seconds//60)
    seconds %= 60
    return str(days) + " dias, " + str(hour) + " horas " + str(minutes) + " min " + str(round(seconds,2)) + "s"

#muestra datos
def showData (data):
    for i in data:
            i_date = stringToDate(i.time)
            i_mail = i.email
            print(f"\t\tFecha {i_date.day}/{i_date.month}/{i_date.year} {i_date.hour}h:{i_date.minute}m:{i_date.second + i_date.microsecond/1000000}s {i_mail}")

def main ():
    campaign = campaignDefined(args.campaign)
    if campaign is not None:
        campaign_summary = api.campaigns.summary(args.campaign) 
        campaign_stats   = campaign_summary.stats.as_dict()
        
        #identificador de la campaña
        print(f"Identificador de la campaña a analizar: {campaign.id}")

        #nombre de la campaña
        print(f"\tNombre de la campaña: {campaign.name}")

        #fecha de comienzo
        launch_date = stringToDate(campaign.launch_date)
        print(f"\tFecha de comienzo: {launch_date.day}/{launch_date.month}/{launch_date.year} a las {launch_date.hour}h:{launch_date.minute}m:{launch_date.second + launch_date.microsecond/1000000}s")

        #numero de enlaces enviados
        number_total_users = campaign_stats.get('total') if campaign_stats.get('total') is not None else 0 
        number_sent_mails  = campaign_stats.get('sent') if campaign_stats.get('sent') is not None else 0
        print(f"\tNumero de emails enviados {number_sent_mails} ({round(number_sent_mails/number_total_users*100,2)}%)")
        sent_mails_data = getResultData (campaign.timeline, SENT_MAILS)
        showData(sent_mails_data)
        

        #numero de emails abiertos
        number_opened_mails = campaign_stats.get('opened') if campaign_stats.get('opened') is not None else 0
        print(f"\tNumero de emails abiertos {number_opened_mails} ({round(number_opened_mails/number_total_users*100,2)}%)")
        opened_mail_data = getResultData(campaign.timeline, OPEN_MAILS)
        showData(opened_mail_data)

        #numero de enlaces pulsados
        number_clicked_mails = campaign_stats.get('clicked') if campaign_stats.get('clicked') is not None else 0
        print(f"\tNumero de enlaces pulsados {number_clicked_mails} ({round(number_clicked_mails/number_sent_mails*100,2)}%)")
        clicked_mails_data = getResultData(campaign.timeline, CLICKED_LINKS)
        showData(clicked_mails_data)
        
        #credenciales enviadas
        number_credential_sent = campaign_stats.get('submitted_data') if campaign_stats.get('submitted_data') is not None else 0
        print(f"\tNumero de credenciales enviadas {number_credential_sent} ({round(number_credential_sent/number_sent_mails*100,2)}%)")
        data_credentials = getResultData(campaign.timeline, SUBMITTED_DATA)
        showData(data_credentials)

        #tiempo transcurrido desde el inicio hasta el envio de la primera credencial
        first_credential = getOccurrence(campaign.timeline, SUBMITTED_DATA, 'FIRST')
        pass_time = diffTimeString(stringToDate(first_credential.time), launch_date)
        print(f"\tTiempo transcurrido desde el inicio de la campaña hasta la primera credencial capturada: \n\t\t {pass_time}")

        #tiempo transcurrido desde el inicio hasta el envio de la ultima credencial
        last_credential = getOccurrence(campaign.timeline, SUBMITTED_DATA, 'LAST')
        pass_time = diffTimeString(stringToDate(last_credential.time), launch_date)
        print(f"\tTiempo transcurrido desde el inicio de la campaña hasta la última credencial capturada: \n\t\t {pass_time}")

        #estadisticas generales
        print(f"Estadísticas generales:")

        #relacion email abiertos / email enviados
        print(f"\t> Se han enviado un total de {number_sent_mails} emails de los cuales se han abierto {number_opened_mails}\n\t emails, lo correspondiente a un {round(number_opened_mails/number_sent_mails*100,2)}%")
        #relacion enlaces pulsados / emails enviados
        print(f"\t> Se han enviado un total de {number_sent_mails} emails de los cuales se han pulsado {number_clicked_mails} enlaces\n\t, lo que corresponde a un {round(number_clicked_mails/number_sent_mails*100,2)}%")
        #relacion credenciales enviadas / emails enviados
        print(f"\t> Se han enviado un total de {number_sent_mails} emails de los cuales se han enviado {number_credential_sent} credenciales, \n\t lo que corresponde a un {round(number_credential_sent/number_sent_mails*100,2)}%")
        #relacion credenciales enviadas / emails abiertos
        print(f"\t> Se han abierto un total de {number_opened_mails} emails de los cuales se han enviado {number_credential_sent} credenciales, \n\t lo que corresponde a un {round(number_credential_sent/number_opened_mails*100,2)}%")
        #relacion credenciales enviadas / enlaces pulsados
        print(f"\t> Se han pulsado un total de {number_clicked_mails} enlaces de los cuales se han enviado {number_credential_sent} credenciales, \n\t lo que corresponde a un {round(number_credential_sent/number_clicked_mails*100,2)}%")
    
    else:
        print(f"No se ha encontrado el nombre de la campaña")
        exit(0)

if __name__ == "__main__":
    main()
    exit(0)