using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laba_No3
{
    class Program
    {
        static void Main(string[] args)
        {
            Processing p = new Processing();
            p.ProcessingText();
            //p.OutputAllSent();
            p.OutputSentencesAscendingWords();
            p.OutputWordsWithChoosenLength();

            Corcondance corcondance = new Corcondance();
            corcondance.CreateListWords();
            corcondance.CreateCorcondance();
            corcondance.ShowCorcondance();
        }
    }
}
