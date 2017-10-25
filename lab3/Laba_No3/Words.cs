using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laba_No3
{
    class Words
    {
        public string word;
        public string before="";
        public string after="";

        public void ProcWords()
        {
            StringBuilder str = new StringBuilder();
            for (int i =0;i<word.Length;i++)
            {
               
                if (i == 0 && !(char.IsLetter(word[0])) && !(char.IsDigit(word[0])))
                {
                    before = word[0].ToString();
                }
                else if (i == word.Length - 1 && !(char.IsLetter(word[word.Length - 1])) && !(char.IsDigit(word[word.Length - 1])))
                {
                    after = word[word.Length - 1].ToString();
                }
                else str.Append(word[i]);
            }
            word = str.ToString();
        }
       
    }
}
