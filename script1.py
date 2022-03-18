from gophish import Gophish
import argparse
from datetime import *
import dateutil

API_KEY = '3fa01a94caf435ca84d8b432b07f2f08892e99568a32f4997dd5dfac3f1a9f3e'
HOST = 'https://localhost:33333'

argument_parser = argparse.ArgumentParser()
argument_parser.add_argument("-c", "--campaign", help="Identificador de la campaña de la que se quiere obtener datos")
args = argument_parser.parse_args()



#instancia de la API de gophish
#api_key: Clave de la API en gophish
#host:    Direccion del servidor donde se aloja gophish
#verify:  Verificación SSL
#               True: uso de certificados
#               False: ignorar el uso de certificados (sin autenticacion)
api = Gophish(api_key= API_KEY, host= HOST, verify= False)

#funcion que convierte una fecha de tipo string a datetime
def stringToDate (string_date):
    return dateutil.parser.parse(string_date)

def campaignDefined (campId):
    for c in api.campaigns.get():
        if str(c.id) == campId:
            return c.id
    return None


def main():
    campId = campaignDefined(args.campaign)
    if campId is not None:
        print(f"ID de la campaña a analizar: {campId}")

        campaign_summary = api.campaigns.summary(campaign_id=campId)
        campaign_stats = campaign_summary.stats.as_dict()

        #nombre de la campaña
        print(f"\tNombre de la campaña: {campaign_summary.name}")

        #fecha de comienzo (lanzamiento de la camapaña)
        launch_date = stringToDate(campaign_summary.launch_date)
        print(f"\tFecha de comienzo: {launch_date.day}/{launch_date.month}/{launch_date.year} a las {launch_date.hour}:{launch_date.minute}:{launch_date.second}")

        #numero de emails enviados (emails enviados / emails totales * 100)
        total_users = campaign_stats.get('total') if campaign_stats.get('total') is not None else 0
        email_sent  = campaign_stats.get('sent')  if campaign_stats.get('sent')  is not None else 0
        print(f"\tEmails enviados: {email_sent} ({round(email_sent / total_users * 100,2)}%)")

        #numero de emails abiertos (emails abiertos / emails enviados * 100)
        open_mails = campaign_stats.get('opened') if campaign_stats.get('opened') is not None else 0
        print(f"\tEmails abiertos: {open_mails} ({round(open_mails / total_users * 100,2)}%)")
    
        #enlaces pulsados (enlaces pulsados / emails enviados * 100)
        clicked_mails = campaign_stats.get('clicked') if campaign_stats.get('opened') is not None else 0
        print(f"\tEnlaces pulsados: {clicked_mails} ({round(clicked_mails/email_sent*100,2)}%)")
    
        #credenciales enviadas (credenciales enviadas / emails enviados * 100)
        submited_data = campaign_stats.get('submitted_data') if campaign_stats.get('submitted_data') is not None else 0
        print(f"\tCredenciales enviadas: {submited_data} ({round(submited_data/email_sent*100,2)}%)")


    
    else:
        print(f"No se ha encontrado el ID de la campaña")
        exit(0)


#CALL
if __name__ == "__main__":
    main()
    exit(0)


