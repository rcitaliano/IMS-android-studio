using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace IMSWeb.Controllers
{
    public class EventsController : ApiController
    {
        IMSWeb.Models.Event[] events = new Models.Event[] {
            new Models.Event { Id = 1, Title = "MeuAmooooor" ,Description="eu gosto tanto de voceeee",ImageURL="ImageURLA"},
            new Models.Event { Id = 2, Title = "TitleB" ,Description="DescriptionB",ImageURL="ImageURLB"},
            new Models.Event { Id = 3, Title = "TitleC" ,Description="DescriptionC",ImageURL="ImageURLC"},
            new Models.Event { Id = 4, Title = "TitleD" ,Description="DescriptionD",ImageURL="ImageURLD"}
        };

        public IEnumerable<Models.Event> GetAllevents()
        {
            return events;
        }

        public IHttpActionResult GetEvent(int id)
        {
            var product = events.FirstOrDefault((p) => p.Id == id);
            if (product == null)
            {
                return NotFound();
            }
            return Ok(product);
        }
    }
}
