package TasteProfile;

/**
* TasteProfile/SongProfileHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./tasteprofile.idl
* Tuesday, 25 September 2018 14:32:55 o'clock CEST
*/

public final class SongProfileHolder implements org.omg.CORBA.portable.Streamable
{
  public TasteProfile.SongProfile value = null;

  public SongProfileHolder ()
  {
  }

  public SongProfileHolder (TasteProfile.SongProfile initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = TasteProfile.SongProfileHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    TasteProfile.SongProfileHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return TasteProfile.SongProfileHelper.type ();
  }

}
