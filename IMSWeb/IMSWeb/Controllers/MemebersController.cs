using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace IMSWeb.Controllers
{
    public class MemebersController : ApiController
    {
        IMSWeb.Models.Member[] Members = new Models.Member[] {
            new Models.Member { Id = 1, Title = "MeuAmooooor" ,Description="eu gosto tanto de voceeee",ImageURL="ImageURLA"},
            new Models.Member { Id = 2, Title = "TitleB" ,Description="DescriptionB",ImageURL="ImageURLB"},
            new Models.Member { Id = 3, Title = "TitleC" ,Description="DescriptionC",ImageURL="ImageURLC"},
            new Models.Member { Id = 4, Title = "TitleD" ,Description="DescriptionD",ImageURL="ImageURLD"}
        };

        public IEnumerable<Models.Member> GetAllMembers()
        {
            return Members;
        }

        public IHttpActionResult GetMember(int id)
        {
            var product = Members.FirstOrDefault((p) => p.Id == id);
            if (product == null)
            {
                return NotFound();
            }
            return Ok(product);
        }
    }
}
